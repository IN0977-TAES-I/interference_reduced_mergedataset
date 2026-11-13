// class name : com.squareup.moshi.ClassJsonAdapter
// method name: FACTORY
// side       : merge
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
   76:       for (Annotation annotation : rawType.getDeclaredAnnotations()) { <== AFFECTED
   77:         if ("kotlin.Metadata".equals(annotation.annotationType().getName())) { <== AFFECTED
   78:           throw new IllegalArgumentException("Cannot serialize Kotlin type " + rawType.getName() <== AFFECTED
   79:               + ". Reflective serialization of Kotlin classes without using kotlin-reflect has " <== AFFECTED
   80:               + "undefined and unexpected behavior. Please use KotlinJsonAdapter from the " <== AFFECTED
   81:               + "moshi-kotlin artifact or use code gen from the moshi-kotlin-codegen artifact."); <== AFFECTED
    }
}
