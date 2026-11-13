// class name : org.jsoup.helper.HttpConnection
// method name: execute(Connection.Request, Response)
// side       : left
// region     : lines 456-485 (affected lines: [456, 483, 484, 485])
class HttpConnection {
    void execute_Connection_Request__Response_() {
  456:                     req.method(Method.GET); // always redirect with a get. any data param from original req are dropped. <== AFFECTED
  457:                     req.data().clear();
  458: 
  459:                     String location = res.header("Location");
  460:                     if (location != null && location.startsWith("http:/") && location.charAt(6) != '/') // fix broken Location: http:/temp/AAG_New/en/index.php
  461:                         location = location.substring(6);
  462:                     req.url(new URL(req.url(), encodeUrl(location)));
  463: 
  464:                     for (Map.Entry<String, String> cookie : res.cookies.entrySet()) { // add response cookies to request (for e.g. login posts)
  465:                         req.cookie(cookie.getKey(), cookie.getValue());
  466:                     }
  467:                     return execute(req, res);
  468:                 }
  469:                 res.req = req;
  470: 
  471:                 // check that we can handle the returned content type; if not, abort before fetching it
  472:                 String contentType = res.contentType();
  473:                 if (contentType != null && !req.ignoreContentType() && (!(contentType.startsWith("text/") || contentType.startsWith("application/xml") || contentType.startsWith("application/xhtml+xml"))))
  474:                     throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml",
  475:                             contentType, req.url().toString());
  476: 
  477:                 InputStream bodyStream = null;
  478:                 InputStream dataStream = null;
  479:                 try {
  480:                     dataStream = conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream();
  481:                     bodyStream = res.hasHeader("Content-Encoding") && res.header("Content-Encoding").equalsIgnoreCase("gzip") ?
  482:                             new BufferedInputStream(new GZIPInputStream(dataStream)) :
  483:                             new BufferedInputStream(dataStream); <== AFFECTED
  484:  <== AFFECTED
  485:                     res.byteData = DataUtil.readToByteBuffer(bodyStream, req.maxBodySize()); <== AFFECTED
    }
}
