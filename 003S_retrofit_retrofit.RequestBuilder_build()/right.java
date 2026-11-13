class RequestBuilder {
    void build__() {
        url.append(requestQuery);
        first = false;
      }
      String[] requestQueryName = methodInfo.requestQueryName;
      for (int i = 0; i < requestQueryName.length; i++) {
        String query = requestQueryName[i];
        if (query != null) {
          Object arg = args[i];
          if (arg != null) {
            String value = URLEncoder.encode(String.valueOf(arg), "UTF-8");
            url.append(first ? '?' : '&').append(query).append('=').append(value);
            first = false;
          }
    }
}
