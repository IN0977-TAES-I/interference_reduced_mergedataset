// class name : com.squareup.moshi.ClassJsonAdapter
// method name: FACTORY
// side       : base
// region     : lines 58-81 (affected lines: [58, 60, 75, 76, 77, 78, 79, 80, 81])
class ClassJsonAdapter {
    void FACTORY() {
   58:       if (Util.isPlatformType(rawType) && !Types.isAllowedPlatformType(rawType)) { <== AFFECTED
   59:         throw new IllegalArgumentException("Platform "
   60:             + typeAnnotatedWithAnnotations(type, annotations) <== AFFECTED
   61:             + " requires explicit JsonAdapter to be registered");
   62:       }
   63:       if (!annotations.isEmpty()) return null;
   64: 
   65:       if (rawType.isAnonymousClass()) {
   66:         throw new IllegalArgumentException("Cannot serialize anonymous class " + rawType.getName());
   67:       }
   68:       if (rawType.isLocalClass()) {
   69:         throw new IllegalArgumentException("Cannot serialize local class " + rawType.getName());
   70:       }
   71:       if (rawType.getEnclosingClass() != null && !Modifier.isStatic(rawType.getModifiers())) {
   72:         throw new IllegalArgumentException(
   73:             "Cannot serialize non-static nested class " + rawType.getName());
   74:       }
   75:       if (Modifier.isAbstract(rawType.getModifiers())) { <== AFFECTED
   76:         throw new IllegalArgumentException("Cannot serialize abstract class " + rawType.getName()); <== AFFECTED
   77:       } <== AFFECTED
   78:  <== AFFECTED
   79:       ClassFactory<Object> classFactory = ClassFactory.get(rawType); <== AFFECTED
   80:       Map<String, FieldBinding<?>> fields = new TreeMap<>(); <== AFFECTED
   81:       for (Type t = type; t != Object.class; t = Types.getGenericSuperclass(t)) { <== AFFECTED
    }
}
