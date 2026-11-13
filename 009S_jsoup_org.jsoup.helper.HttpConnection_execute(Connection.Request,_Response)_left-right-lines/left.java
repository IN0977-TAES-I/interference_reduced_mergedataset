// class name : org.jsoup.helper.HttpConnection
// method name: execute(Connection.Request, Response)
// side       : left
// region     : lines 544-586 (affected lines: [544, 547, 584, 585, 586])
class HttpConnection {
    void execute_Connection_Request__Response_() {
  544:                     req.data().clear(); <== AFFECTED
  545: 
  546:                     String location = res.header(LOCATION);
  547:                     if (location != null && location.startsWith("http:/") && location.charAt(6) != '/') // fix broken Location: http:/temp/AAG_New/en/index.php <== AFFECTED
  548:                         location = location.substring(6);
  549:                     req.url(StringUtil.resolve(req.url(), encodeUrl(location)));
  550: 
  551:                     for (Map.Entry<String, String> cookie : res.cookies.entrySet()) { // add response cookies to request (for e.g. login posts)
  552:                         req.cookie(cookie.getKey(), cookie.getValue());
  553:                     }
  554:                     return execute(req, res);
  555:                 }
  556:                 if ((status < 200 || status >= 400) && !req.ignoreHttpErrors())
  557:                         throw new HttpStatusException("HTTP error fetching URL", status, req.url().toString());
  558: 
  559:                 // check that we can handle the returned content type; if not, abort before fetching it
  560:                 String contentType = res.contentType();
  561:                 if (contentType != null
  562:                         && !req.ignoreContentType()
  563:                         && !contentType.startsWith("text/")
  564:                         && !xmlContentTypeRxp.matcher(contentType).matches()
  565:                         )
  566:                     throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml",
  567:                             contentType, req.url().toString());
  568: 
  569:                 // switch to the XML parser if content type is xml and not parser not explicitly set
  570:                 if (contentType != null && xmlContentTypeRxp.matcher(contentType).matches()) {
  571:                     // only flip it if a HttpConnection.Request (i.e. don't presume other impls want it):
  572:                     if (req instanceof HttpConnection.Request && !((Request) req).parserDefined) {
  573:                         req.parser(Parser.xmlParser());
  574:                     }
  575:                 }
  576: 
  577:                 res.charset = DataUtil.getCharsetFromContentType(res.contentType); // may be null, readInputStream deals with it
  578:                 if (conn.getContentLength() != 0) { // -1 means unknown, chunked. sun throws an IO exception on 500 response with no content when trying to read body
  579:                     InputStream bodyStream = null;
  580:                     try {
  581:                         bodyStream = conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream();
  582:                         if (res.hasHeaderWithValue(CONTENT_ENCODING, "gzip"))
  583:                             bodyStream = new GZIPInputStream(bodyStream);
  584:  <== AFFECTED
  585:                         res.byteData = DataUtil.readToByteBuffer(bodyStream, req.maxBodySize()); <== AFFECTED
  586:                     } finally { <== AFFECTED
    }
}
