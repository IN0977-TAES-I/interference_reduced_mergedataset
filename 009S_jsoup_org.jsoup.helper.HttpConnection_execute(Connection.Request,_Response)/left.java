class HttpConnection {
    static String CONTENT_ENCODING;
    static String LOCATION;
    static Pattern xmlContentTypeRxp;

    static Response execute(Connection.Request req, Response previousResponse) throws IOException {
        Validate.notNull(req, "Request must not be null");
        String protocol = req.url().getProtocol();
        if (!protocol.equals("http") && !protocol.equals("https"))
            throw new MalformedURLException("Only http & https protocols supported");

        String mimeBoundary = null;
        if (!req.method().hasBody() && req.data().size() > 0) {
            serialiseRequestUrl(req);
        } else if (req.method().hasBody()) {
            mimeBoundary = setOutputContentType(req);
        }
        HttpURLConnection conn = createConnection(req);
        Response res;
        try {
            conn.connect();
            if (conn.getDoOutput())
                writePost(req, conn.getOutputStream(), mimeBoundary);

            int status = conn.getResponseCode();
            res = new Response(previousResponse);
            res.setupFromConnection(conn, previousResponse);
            res.req = req;

            if (res.hasHeader(LOCATION) && req.followRedirects()) {
                req.method(Method.GET);
                req.data().clear();

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

                    res.byteData = DataUtil.readToByteBuffer(bodyStream, req.maxBodySize());
                } finally {
                    if (bodyStream != null) bodyStream.close();
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
}
