// class name : org.jsoup.helper.HttpConnection
// method name: execute(Connection.Request, Response)
// side       : base
// region     : lines 456-485 (affected lines: [456, 483, 484, 485])
class HttpConnection {
    void execute_Connection_Request__Response_() {
  456:  <== AFFECTED
  457:                     String location = res.header("Location");
  458:                     if (location != null && location.startsWith("http:/") && location.charAt(6) != '/') // fix broken Location: http:/temp/AAG_New/en/index.php
  459:                         location = location.substring(6);
  460:                     req.url(new URL(req.url(), encodeUrl(location)));
  461: 
  462:                     for (Map.Entry<String, String> cookie : res.cookies.entrySet()) { // add response cookies to request (for e.g. login posts)
  463:                         req.cookie(cookie.getKey(), cookie.getValue());
  464:                     }
  465:                     return execute(req, res);
  466:                 }
  467:                 res.req = req;
  468: 
  469:                 // check that we can handle the returned content type; if not, abort before fetching it
  470:                 String contentType = res.contentType();
  471:                 if (contentType != null && !req.ignoreContentType() && (!(contentType.startsWith("text/") || contentType.startsWith("application/xml") || contentType.startsWith("application/xhtml+xml"))))
  472:                     throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml",
  473:                             contentType, req.url().toString());
  474: 
  475:                 InputStream bodyStream = null;
  476:                 InputStream dataStream = null;
  477:                 try {
  478:                     dataStream = conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream();
  479:                     bodyStream = res.hasHeader("Content-Encoding") && res.header("Content-Encoding").equalsIgnoreCase("gzip") ?
  480:                             new BufferedInputStream(new GZIPInputStream(dataStream)) :
  481:                             new BufferedInputStream(dataStream);
  482: 
  483:                     res.byteData = DataUtil.readToByteBuffer(bodyStream, req.maxBodySize()); <== AFFECTED
  484:                     res.charset = DataUtil.getCharsetFromContentType(res.contentType); // may be null, readInputStream deals with it <== AFFECTED
  485:                 } finally { <== AFFECTED
    }
}
