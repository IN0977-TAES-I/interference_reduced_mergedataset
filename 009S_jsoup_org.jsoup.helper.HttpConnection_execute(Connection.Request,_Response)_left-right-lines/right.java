// class name : org.jsoup.helper.HttpConnection
// method name: execute(Connection.Request, Response)
// side       : right
// region     : lines 544-586 (affected lines: [544, 547, 584, 585, 586])
class HttpConnection {
    void execute_Connection_Request__Response_() {
  544:                 if (contentType != null <== AFFECTED
  545:                         && !req.ignoreContentType()
  546:                         && !contentType.startsWith("text/")
  547:                         && !xmlContentTypeRxp.matcher(contentType).matches() <== AFFECTED
  548:                         )
  549:                     throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml",
  550:                             contentType, req.url().toString());
  551: 
  552:                 // switch to the XML parser if content type is xml and not parser not explicitly set
  553:                 if (contentType != null && xmlContentTypeRxp.matcher(contentType).matches()) {
  554:                     // only flip it if a HttpConnection.Request (i.e. don't presume other impls want it):
  555:                     if (req instanceof HttpConnection.Request && !((Request) req).parserDefined) {
  556:                         req.parser(Parser.xmlParser());
  557:                     }
  558:                 }
  559: 
  560:                 res.charset = DataUtil.getCharsetFromContentType(res.contentType); // may be null, readInputStream deals with it
  561:                 if (conn.getContentLength() != 0) { // -1 means unknown, chunked. sun throws an IO exception on 500 response with no content when trying to read body
  562:                     InputStream bodyStream = null;
  563:                     InputStream dataStream = null;
  564:                     try {
  565:                         dataStream = conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream();
  566:                         bodyStream = res.hasHeaderWithValue(CONTENT_ENCODING, "gzip") ?
  567:                                 new BufferedInputStream(new GZIPInputStream(dataStream)) :
  568:                                 new BufferedInputStream(dataStream);
  569: 
  570:                         res.byteData = DataUtil.readToByteBuffer(bodyStream, req.maxBodySize());
  571:                     } finally {
  572:                         if (bodyStream != null) bodyStream.close();
  573:                         if (dataStream != null) dataStream.close();
  574:                     }
  575:                 } else {
  576:                     res.byteData = DataUtil.emptyByteBuffer();
  577:                 }
  578:             } finally {
  579:                 // per Java's documentation, this is not necessary, and precludes keepalives. However in practise,
  580:                 // connection errors will not be released quickly enough and can cause a too many open files error.
  581:                 conn.disconnect();
  582:             }
  583: 
  584:             res.executed = true; <== AFFECTED
  585:             return res; <== AFFECTED
  586:         } <== AFFECTED
    }
}
