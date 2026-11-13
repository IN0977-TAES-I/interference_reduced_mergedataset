// class name : org.fusesource.restygwt.rebind.DirectRestServiceInterfaceClassCreator
// method name: getMethodCallback(JMethod)
// side       : base
// region     : lines 83-91 (affected lines: [83, 84, 85, 86, 87, 88, 89, 90, 91])
class DirectRestServiceInterfaceClassCreator {
    void getMethodCallback_JMethod_() {
   83:         } <== AFFECTED
   84:         return "org.fusesource.restygwt.client.MethodCallback<" + method.getReturnType().getParameterizedQualifiedSourceName() + "> callback"; <== AFFECTED
   85:     } <== AFFECTED
   86:  <== AFFECTED
   87:     private String getAnnotationsAsString(Annotation[] annotations) { <== AFFECTED
   88:         StringBuilder result = new StringBuilder(""); <== AFFECTED
   89:         OnceFirstIterator<String> space = new OnceFirstIterator<String>("", " "); <== AFFECTED
   90:  <== AFFECTED
   91:         for (String annotation : getAnnotationsAsStringArray(annotations)) { <== AFFECTED
    }
}
