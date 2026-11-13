// class name : retrofit.RequestBuilder
// method name: build()
// side       : base
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
   95:           String value = URLEncoder.encode(String.valueOf(args[i]), "UTF-8"); <== AFFECTED
   96:           url.append(first ? '?' : '&').append(query).append('=').append(value); <== AFFECTED
   97:           first = false; <== AFFECTED
   98:         }
   99:       }
  100:     } <== AFFECTED
    }
}
