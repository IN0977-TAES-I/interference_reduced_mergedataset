// class name : org.jsoup.helper.HttpConnection
// method name: execute(Connection.Request, Response)
// side       : merge
// region     : lines 456-485 (affected lines: [456, 483, 484, 485])
class HttpConnection {
    void execute_Connection_Request__Response_() {
  456:                     if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER || status == HTTP_TEMP_REDIR) <== AFFECTED
  457:                         needsRedirect = true;
  458:                     else if (!req.ignoreHttpErrors())
  459:                         throw new HttpStatusException("HTTP error fetching URL", status, req.url().toString());
  460:                 }
  461:                 res = new Response(previousResponse);
  462:                 res.setupFromConnection(conn, previousResponse);
  463:                 if (needsRedirect && req.followRedirects()) {
  464:                     req.method(Method.GET); // always redirect with a get. any data param from original req are dropped.
  465:                     req.data().clear();
  466: 
  467:                     String location = res.header("Location");
  468:                     if (location != null && location.startsWith("http:/") && location.charAt(6) != '/') // fix broken Location: http:/temp/AAG_New/en/index.php
  469:                         location = location.substring(6);
  470:                     req.url(new URL(req.url(), encodeUrl(location)));
  471: 
  472:                     for (Map.Entry<String, String> cookie : res.cookies.entrySet()) { // add response cookies to request (for e.g. login posts)
  473:                         req.cookie(cookie.getKey(), cookie.getValue());
  474:                     }
  475:                     return execute(req, res);
  476:                 }
  477:                 res.req = req;
  478: 
  479:                 // check that we can handle the returned content type; if not, abort before fetching it
  480:                 String contentType = res.contentType();
  481:                 if (contentType != null
  482:                         && !req.ignoreContentType()
  483:                         && !contentType.startsWith("text/") <== AFFECTED
  484:                         && !contentType.startsWith("application/xml") <== AFFECTED
  485:                         && !xmlContentTypeRxp.matcher(contentType).matches() <== AFFECTED
    }
}
