class ClassJsonAdapter {
    void FACTORY() {
      if (Util.isPlatformType(rawType) && !Types.isAllowedPlatformType(rawType)) {
        throw new IllegalArgumentException("Platform "
            + typeAnnotatedWithAnnotations(type, annotations)
            + " requires explicit JsonAdapter to be registered");
      }
      if (!annotations.isEmpty()) return null;

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

      ClassFactory<Object> classFactory = ClassFactory.get(rawType);
      Map<String, FieldBinding<?>> fields = new TreeMap<>();
      for (Type t = type; t != Object.class; t = Types.getGenericSuperclass(t)) {
    }
}
