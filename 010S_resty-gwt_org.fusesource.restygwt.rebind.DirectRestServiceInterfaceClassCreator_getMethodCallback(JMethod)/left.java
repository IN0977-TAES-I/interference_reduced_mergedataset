class DirectRestServiceInterfaceClassCreator {
    void getMethodCallback_JMethod_() {
            return "org.fusesource.restygwt.client.MethodCallback<" + primitiveType.getQualifiedBoxedSourceName() + "> callback";
        }
        return "org.fusesource.restygwt.client.MethodCallback<" + method.getReturnType().getParameterizedQualifiedSourceName() + "> callback";
    }

    private String getAnnotationsAsString(Annotation[] annotations) {
        StringBuilder result = new StringBuilder("");
        OnceFirstIterator<String> space = new OnceFirstIterator<String>("", " ");

    }
}
