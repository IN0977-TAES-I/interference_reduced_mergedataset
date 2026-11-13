class HttpConnection {
    void execute_Connection_Request__Response_() {
                        && !xmlContentTypeRxp.matcher(contentType).matches()
                        )
                    throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml",
                            contentType, req.url().toString());

                if (contentType != null && xmlContentTypeRxp.matcher(contentType).matches()) {
                    if (req instanceof HttpConnection.Request && !((Request) req).parserDefined) {
                        req.parser(Parser.xmlParser());
                    }
                }

                res.charset = DataUtil.getCharsetFromContentType(res.contentType);
                if (conn.getContentLength() != 0) {
                    InputStream bodyStream = null;
                    InputStream dataStream = null;
                    try {
                        dataStream = conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream();
                        bodyStream = res.hasHeaderWithValue(CONTENT_ENCODING, "gzip") ?
                                new BufferedInputStream(new GZIPInputStream(dataStream)) :
                                new BufferedInputStream(dataStream);

                        res.byteData = DataUtil.readToByteBuffer(bodyStream, req.maxBodySize());
                    } finally {
                        if (bodyStream != null) bodyStream.close();
                        if (dataStream != null) dataStream.close();
                    }
                } else {
                    res.byteData = DataUtil.emptyByteBuffer();
                }
            } finally {
                conn.disconnect();
            }

            res.executed = true;
            return res;
        }

        public int statusCode() {
            return statusCode;
    }
}
