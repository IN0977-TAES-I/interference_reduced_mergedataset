// class name : org.jsoup.helper.HttpConnection
// method name: execute(Connection.Request, Response)
// side       : merge
// region     : lines 544-586 (affected lines: [544, 547, 584, 585, 586])
class HttpConnection {
    void execute_Connection_Request__Response_() {
  544:                     if (status != HTTP_TEMP_REDIR) { <== AFFECTED
  545:                         req.method(Method.GET); // always redirect with a get. any data param from original req are dropped.
  546:                         req.data().clear();
  547:                     } <== AFFECTED
  548: 
  549:                     String location = res.header(LOCATION);
  550:                     if (location != null && location.startsWith("http:/") && location.charAt(6) != '/') // fix broken Location: http:/temp/AAG_New/en/index.php
  551:                         location = location.substring(6);
  552:                     req.url(StringUtil.resolve(req.url(), encodeUrl(location)));
  553: 
  554:                     for (Map.Entry<String, String> cookie : res.cookies.entrySet()) { // add response cookies to request (for e.g. login posts)
  555:                         req.cookie(cookie.getKey(), cookie.getValue());
  556:                     }
  557:                     return execute(req, res);
  558:                 }
  559:                 if ((status < 200 || status >= 400) && !req.ignoreHttpErrors())
  560:                         throw new HttpStatusException("HTTP error fetching URL", status, req.url().toString());
  561: 
  562:                 // check that we can handle the returned content type; if not, abort before fetching it
  563:                 String contentType = res.contentType();
  564:                 if (contentType != null
  565:                         && !req.ignoreContentType()
  566:                         && !contentType.startsWith("text/")
  567:                         && !xmlContentTypeRxp.matcher(contentType).matches()
  568:                         )
  569:                     throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml",
  570:                             contentType, req.url().toString());
  571: 
  572:                 // switch to the XML parser if content type is xml and not parser not explicitly set
  573:                 if (contentType != null && xmlContentTypeRxp.matcher(contentType).matches()) {
  574:                     // only flip it if a HttpConnection.Request (i.e. don't presume other impls want it):
  575:                     if (req instanceof HttpConnection.Request && !((Request) req).parserDefined) {
  576:                         req.parser(Parser.xmlParser());
  577:                     }
  578:                 }
  579: 
  580:                 res.charset = DataUtil.getCharsetFromContentType(res.contentType); // may be null, readInputStream deals with it
  581:                 if (conn.getContentLength() != 0) { // -1 means unknown, chunked. sun throws an IO exception on 500 response with no content when trying to read body
  582:                     InputStream bodyStream = null;
  583:                     try {
  584:                         bodyStream = conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream(); <== AFFECTED
  585:                         if (res.hasHeaderWithValue(CONTENT_ENCODING, "gzip")) <== AFFECTED
  586:                             bodyStream = new GZIPInputStream(bodyStream); <== AFFECTED
    }
}
