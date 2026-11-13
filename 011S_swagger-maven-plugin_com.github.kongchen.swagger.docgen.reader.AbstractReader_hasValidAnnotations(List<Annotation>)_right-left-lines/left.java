// class name : com.github.kongchen.swagger.docgen.reader.AbstractReader
// method name: hasValidAnnotations(List<Annotation>)
// side       : left
// region     : lines 342-343 (affected lines: [342, 343])
class AbstractReader {
    void hasValidAnnotations_List_Annotation__() {
  342:         validParameterAnnotations.add(RequestParam.class); <== AFFECTED
  343:         validParameterAnnotations.add(RequestBody.class); <== AFFECTED
    }
}
