// class name : org.jsoup.helper.HttpConnection
// method name: execute(Connection.Request, Response)
// side       : base
// region     : lines 544-586 (affected lines: [544, 547, 584, 585, 586])
class HttpConnection {
    void execute_Connection_Request__Response_() {
  544:                         && !xmlContentTypeRxp.matcher(contentType).matches() <== AFFECTED
  545:                         )
  546:                     throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml",
  547:                             contentType, req.url().toString()); <== AFFECTED
  548: 
  549:                 // switch to the XML parser if content type is xml and not parser not explicitly set
  550:                 if (contentType != null && xmlContentTypeRxp.matcher(contentType).matches()) {
  551:                     // only flip it if a HttpConnection.Request (i.e. don't presume other impls want it):
  552:                     if (req instanceof HttpConnection.Request && !((Request) req).parserDefined) {
  553:                         req.parser(Parser.xmlParser());
  554:                     }
  555:                 }
  556: 
  557:                 res.charset = DataUtil.getCharsetFromContentType(res.contentType); // may be null, readInputStream deals with it
  558:                 if (conn.getContentLength() != 0) { // -1 means unknown, chunked. sun throws an IO exception on 500 response with no content when trying to read body
  559:                     InputStream bodyStream = null;
  560:                     InputStream dataStream = null;
  561:                     try {
  562:                         dataStream = conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream();
  563:                         bodyStream = res.hasHeaderWithValue(CONTENT_ENCODING, "gzip") ?
  564:                                 new BufferedInputStream(new GZIPInputStream(dataStream)) :
  565:                                 new BufferedInputStream(dataStream);
  566: 
  567:                         res.byteData = DataUtil.readToByteBuffer(bodyStream, req.maxBodySize());
  568:                     } finally {
  569:                         if (bodyStream != null) bodyStream.close();
  570:                         if (dataStream != null) dataStream.close();
  571:                     }
  572:                 } else {
  573:                     res.byteData = DataUtil.emptyByteBuffer();
  574:                 }
  575:             } finally {
  576:                 // per Java's documentation, this is not necessary, and precludes keepalives. However in practise,
  577:                 // connection errors will not be released quickly enough and can cause a too many open files error.
  578:                 conn.disconnect();
  579:             }
  580: 
  581:             res.executed = true;
  582:             return res;
  583:         }
  584:  <== AFFECTED
  585:         public int statusCode() { <== AFFECTED
  586:             return statusCode; <== AFFECTED
    }
}
