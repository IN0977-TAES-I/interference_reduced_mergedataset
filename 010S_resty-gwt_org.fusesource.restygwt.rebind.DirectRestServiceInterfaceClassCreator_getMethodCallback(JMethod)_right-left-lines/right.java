// class name : org.fusesource.restygwt.rebind.DirectRestServiceInterfaceClassCreator
// method name: getMethodCallback(JMethod)
// side       : right
// region     : lines 83-91 (affected lines: [83, 84, 85, 86, 87, 88, 89, 90, 91])
class DirectRestServiceInterfaceClassCreator {
    void getMethodCallback_JMethod_() {
   83:         if (isVoidMethod(method)) { <== AFFECTED
   84:             return "org.fusesource.restygwt.client.MethodCallback<java.lang.Void> callback"; <== AFFECTED
   85:         } <== AFFECTED
   86:         final String returnType = method.getReturnType().getParameterizedQualifiedSourceName(); <== AFFECTED
   87:         if (isOverlayMethod(method)) { <== AFFECTED
   88:             return "org.fusesource.restygwt.client.OverlayCallback<" + returnType + "> callback"; <== AFFECTED
   89:         } else { <== AFFECTED
   90:             return "org.fusesource.restygwt.client.MethodCallback<" + returnType + "> callback"; <== AFFECTED
   91:         } <== AFFECTED
    }
}
