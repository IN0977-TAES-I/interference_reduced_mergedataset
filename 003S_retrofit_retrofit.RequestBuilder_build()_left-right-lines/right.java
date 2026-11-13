// class name : retrofit.RequestBuilder
// method name: build()
// side       : right
// region     : lines 88-100 (affected lines: [88, 95, 96, 97, 100])
class RequestBuilder {
    void build__() {
   88:         url.append(requestQuery); <== AFFECTED
   89:         first = false;
   90:       }
   91:       String[] requestQueryName = methodInfo.requestQueryName;
   92:       for (int i = 0; i < requestQueryName.length; i++) {
   93:         String query = requestQueryName[i];
   94:         if (query != null) {
   95:           Object arg = args[i]; <== AFFECTED
   96:           if (arg != null) { // Null values are skipped. <== AFFECTED
   97:             String value = URLEncoder.encode(String.valueOf(arg), "UTF-8"); <== AFFECTED
   98:             url.append(first ? '?' : '&').append(query).append('=').append(value);
   99:             first = false;
  100:           } <== AFFECTED
    }
}
