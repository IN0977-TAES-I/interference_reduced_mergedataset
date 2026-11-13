// class name : org.fusesource.restygwt.rebind.DirectRestServiceInterfaceClassCreator
// method name: getMethodCallback(JMethod)
// side       : merge
// region     : lines 83-91 (affected lines: [83, 84, 85, 86, 87, 88, 89, 90, 91])
class DirectRestServiceInterfaceClassCreator {
    void getMethodCallback_JMethod_() {
   83:         if (method.getReturnType().isPrimitive() != null) { <== AFFECTED
   84:             JPrimitiveType primitiveType = method.getReturnType().isPrimitive(); <== AFFECTED
   85:             return "org.fusesource.restygwt.client.MethodCallback<" + primitiveType.getQualifiedBoxedSourceName() + "> callback"; <== AFFECTED
   86:         } <== AFFECTED
   87:         final String returnType = method.getReturnType().getParameterizedQualifiedSourceName(); <== AFFECTED
   88:         if (isOverlayMethod(method)) { <== AFFECTED
   89:             return "org.fusesource.restygwt.client.OverlayCallback<" + returnType + "> callback"; <== AFFECTED
   90:         } else { <== AFFECTED
   91:             return "org.fusesource.restygwt.client.MethodCallback<" + returnType + "> callback"; <== AFFECTED
    }
}
