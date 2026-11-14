class ClassJsonAdapter {
    public static final JsonAdapter.Factory FACTORY = new JsonAdapter.Factory() {
      @Override public @Nullable JsonAdapter<?> create(
          Type type, Set<? extends Annotation> annotations, Moshi moshi) {
        if (!(type instanceof Class) && !(type instanceof ParameterizedType)) {
          return null;
        }
        Class<?> rawType = Types.getRawType(type);
        if (rawType.isInterface() || rawType.isEnum()) return null;
        if (!annotations.isEmpty()) return null;
        if (Util.isPlatformType(rawType)) {
          throw new IllegalArgumentException(
              "Platform " + type + " requires explicit JsonAdapter to be registered");
        }

        if (rawType.isAnonymousClass()) {
          throw new IllegalArgumentException("Cannot serialize anonymous class " + rawType.getName());
        }
        if (rawType.isLocalClass()) {
          throw new IllegalArgumentException("Cannot serialize local class " + rawType.getName());
        }
        if (rawType.getEnclosingClass() != null && !Modifier.isStatic(rawType.getModifiers())) {
          throw new IllegalArgumentException(
              "Cannot serialize non-static nested class " + rawType.getName());
        }
        if (Modifier.isAbstract(rawType.getModifiers())) {
          throw new IllegalArgumentException("Cannot serialize abstract class " + rawType.getName());
        }
        for (Annotation annotation : rawType.getDeclaredAnnotations()) {
          if ("kotlin.Metadata".equals(annotation.annotationType().getName())) {
            throw new IllegalArgumentException("Cannot serialize Kotlin type " + rawType.getName()
                + ". Reflective serialization of Kotlin classes without using kotlin-reflect has "
                + "undefined and unexpected behavior. Please use KotlinJsonAdapter from the "
                + "moshi-kotlin artifact or use code gen from the moshi-kotlin-codegen artifact.");
          }
        }

        ClassFactory<Object> classFactory = ClassFactory.get(rawType);
        Map<String, FieldBinding<?>> fields = new TreeMap<>();
        for (Type t = type; t != Object.class; t = Types.getGenericSuperclass(t)) {
          createFieldBindings(moshi, t, fields);
        }
        return new ClassJsonAdapter<>(classFactory, fields).nullSafe();
      }

      private void createFieldBindings(
          Moshi moshi, Type type, Map<String, FieldBinding<?>> fieldBindings) {
        Class<?> rawType = Types.getRawType(type);
        boolean platformType = Util.isPlatformType(rawType);
        for (Field field : rawType.getDeclaredFields()) {
          if (!includeField(platformType, field.getModifiers())) continue;

          Type fieldType = resolve(type, rawType, field.getGenericType());
          Set<? extends Annotation> annotations = Util.jsonAnnotations(field);
          String fieldName = field.getName();
          JsonAdapter<Object> adapter = moshi.adapter(fieldType, annotations, fieldName);

          field.setAccessible(true);

          Json jsonAnnotation = field.getAnnotation(Json.class);
          String name = jsonAnnotation != null ? jsonAnnotation.name() : fieldName;
          FieldBinding<Object> fieldBinding = new FieldBinding<>(name, field, adapter);
          FieldBinding<?> replaced = fieldBindings.put(name, fieldBinding);
          if (replaced != null) {
            throw new IllegalArgumentException("Conflicting fields:\n"
                + "    " + replaced.field + "\n"
                + "    " + fieldBinding.field);
          }
        }
      }

      private boolean includeField(boolean platformType, int modifiers) {
        if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) return false;
        return Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers) || !platformType;
      }
    };
}
