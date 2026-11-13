class HttpConnection {
    void execute_Connection_Request__Response_() {
                    req.method(Method.GET);
                    req.data().clear();

                    String location = res.header("Location");
                    if (location != null && location.startsWith("http:/") && location.charAt(6) != '/')
                        location = location.substring(6);
                    req.url(new URL(req.url(), encodeUrl(location)));

                    for (Map.Entry<String, String> cookie : res.cookies.entrySet()) {
                        req.cookie(cookie.getKey(), cookie.getValue());
                    }
                    return execute(req, res);
                }
                res.req = req;

                String contentType = res.contentType();
                if (contentType != null && !req.ignoreContentType() && (!(contentType.startsWith("text/") || contentType.startsWith("application/xml") || contentType.startsWith("application/xhtml+xml"))))
                    throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml",
                            contentType, req.url().toString());

                InputStream bodyStream = null;
                InputStream dataStream = null;
                try {
                    dataStream = conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream();
                    bodyStream = res.hasHeader("Content-Encoding") && res.header("Content-Encoding").equalsIgnoreCase("gzip") ?
                            new BufferedInputStream(new GZIPInputStream(dataStream)) :
                            new BufferedInputStream(dataStream);

                    res.byteData = DataUtil.readToByteBuffer(bodyStream, req.maxBodySize());
    }
}
