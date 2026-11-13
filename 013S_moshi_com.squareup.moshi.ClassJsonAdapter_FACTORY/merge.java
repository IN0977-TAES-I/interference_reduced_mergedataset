class ClassJsonAdapter {
    void FACTORY() {
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
