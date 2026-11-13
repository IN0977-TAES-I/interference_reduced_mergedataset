// class name : org.jsoup.helper.HttpConnection
// method name: execute(Connection.Request, Response)
// side       : right
// region     : lines 456-485 (affected lines: [456, 483, 484, 485])
class HttpConnection {
    void execute_Connection_Request__Response_() {
  456:                     else if (!req.ignoreHttpErrors()) <== AFFECTED
  457:                         throw new HttpStatusException("HTTP error fetching URL", status, req.url().toString());
  458:                 }
  459:                 res = new Response(previousResponse);
  460:                 res.setupFromConnection(conn, previousResponse);
  461:                 if (needsRedirect && req.followRedirects()) {
  462:                     req.method(Method.GET); // always redirect with a get. any data param from original req are dropped.
  463:                     req.data().clear();
  464: 
  465:                     String location = res.header("Location");
  466:                     if (location != null && location.startsWith("http:/") && location.charAt(6) != '/') // fix broken Location: http:/temp/AAG_New/en/index.php
  467:                         location = location.substring(6);
  468:                     req.url(new URL(req.url(), encodeUrl(location)));
  469: 
  470:                     for (Map.Entry<String, String> cookie : res.cookies.entrySet()) { // add response cookies to request (for e.g. login posts)
  471:                         req.cookie(cookie.getKey(), cookie.getValue());
  472:                     }
  473:                     return execute(req, res);
  474:                 }
  475:                 res.req = req;
  476: 
  477:                 // check that we can handle the returned content type; if not, abort before fetching it
  478:                 String contentType = res.contentType();
  479:                 if (contentType != null
  480:                         && !req.ignoreContentType()
  481:                         && !contentType.startsWith("text/")
  482:                         && !contentType.startsWith("application/xml")
  483:                         && !xmlContentTypeRxp.matcher(contentType).matches() <== AFFECTED
  484:                         ) <== AFFECTED
  485:                     throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml", <== AFFECTED
    }
}
