class HttpConnection {
    void execute_Connection_Request__Response_() {
                    if (status != HTTP_TEMP_REDIR) {
                        req.method(Method.GET);
                        req.data().clear();
                    }

                    String location = res.header(LOCATION);
                    if (location != null && location.startsWith("http:/") && location.charAt(6) != '/')
                        location = location.substring(6);
                    req.url(StringUtil.resolve(req.url(), encodeUrl(location)));

                    for (Map.Entry<String, String> cookie : res.cookies.entrySet()) {
                        req.cookie(cookie.getKey(), cookie.getValue());
                    }
                    return execute(req, res);
                }
                if ((status < 200 || status >= 400) && !req.ignoreHttpErrors())
                        throw new HttpStatusException("HTTP error fetching URL", status, req.url().toString());

                String contentType = res.contentType();
                if (contentType != null
                        && !req.ignoreContentType()
                        && !contentType.startsWith("text/")
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
                    try {
                        bodyStream = conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream();
                        if (res.hasHeaderWithValue(CONTENT_ENCODING, "gzip"))
                            bodyStream = new GZIPInputStream(bodyStream);
    }
}
