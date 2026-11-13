// class name : com.github.kongchen.swagger.docgen.reader.AbstractReader
// method name: hasValidAnnotations(List<Annotation>)
// side       : base
// region     : lines 342-343 (affected lines: [342, 343])
class AbstractReader {
    void hasValidAnnotations_List_Annotation__() {
  342:             if (validParameterAnnotations.contains(potentialAnnotation.annotationType())) { <== AFFECTED
  343:                 hasValidAnnotation = true; <== AFFECTED
    }
}
