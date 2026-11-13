// class name : org.fusesource.restygwt.rebind.DirectRestServiceInterfaceClassCreator
// method name: getMethodCallback(JMethod)
// side       : left
// region     : lines 83-91 (affected lines: [83, 84, 85, 86, 87, 88, 89, 90, 91])
class DirectRestServiceInterfaceClassCreator {
    void getMethodCallback_JMethod_() {
   83:             return "org.fusesource.restygwt.client.MethodCallback<" + primitiveType.getQualifiedBoxedSourceName() + "> callback"; <== AFFECTED
   84:         } <== AFFECTED
   85:         return "org.fusesource.restygwt.client.MethodCallback<" + method.getReturnType().getParameterizedQualifiedSourceName() + "> callback"; <== AFFECTED
   86:     } <== AFFECTED
   87:  <== AFFECTED
   88:     private String getAnnotationsAsString(Annotation[] annotations) { <== AFFECTED
   89:         StringBuilder result = new StringBuilder(""); <== AFFECTED
   90:         OnceFirstIterator<String> space = new OnceFirstIterator<String>("", " "); <== AFFECTED
   91:  <== AFFECTED
    }
}
