// class name : com.squareup.moshi.ClassJsonAdapter
// method name: FACTORY
// side       : right
// region     : lines 58-81 (affected lines: [58, 60, 75, 76, 77, 78, 79, 80, 81])
class ClassJsonAdapter {
    void FACTORY() {
   58:       if (Util.isPlatformType(rawType)) { <== AFFECTED
   59:         throw new IllegalArgumentException(
   60:             "Platform " + type + " requires explicit JsonAdapter to be registered"); <== AFFECTED
   61:       }
   62: 
   63:       if (rawType.isAnonymousClass()) {
   64:         throw new IllegalArgumentException("Cannot serialize anonymous class " + rawType.getName());
   65:       }
   66:       if (rawType.isLocalClass()) {
   67:         throw new IllegalArgumentException("Cannot serialize local class " + rawType.getName());
   68:       }
   69:       if (rawType.getEnclosingClass() != null && !Modifier.isStatic(rawType.getModifiers())) {
   70:         throw new IllegalArgumentException(
   71:             "Cannot serialize non-static nested class " + rawType.getName());
   72:       }
   73:       if (Modifier.isAbstract(rawType.getModifiers())) {
   74:         throw new IllegalArgumentException("Cannot serialize abstract class " + rawType.getName());
   75:       } <== AFFECTED
   76:  <== AFFECTED
   77:       ClassFactory<Object> classFactory = ClassFactory.get(rawType); <== AFFECTED
   78:       Map<String, FieldBinding<?>> fields = new TreeMap<>(); <== AFFECTED
   79:       for (Type t = type; t != Object.class; t = Types.getGenericSuperclass(t)) { <== AFFECTED
   80:         createFieldBindings(moshi, t, fields); <== AFFECTED
   81:       } <== AFFECTED
    }
}
