class DirectRestServiceInterfaceClassCreator {
    void getMethodCallback_JMethod_() {
        if (method.getReturnType().isPrimitive() != null) {
            JPrimitiveType primitiveType = method.getReturnType().isPrimitive();
            return "org.fusesource.restygwt.client.MethodCallback<" + primitiveType.getQualifiedBoxedSourceName() + "> callback";
        }
        final String returnType = method.getReturnType().getParameterizedQualifiedSourceName();
        if (isOverlayMethod(method)) {
            return "org.fusesource.restygwt.client.OverlayCallback<" + returnType + "> callback";
        } else {
            return "org.fusesource.restygwt.client.MethodCallback<" + returnType + "> callback";
    }
}
