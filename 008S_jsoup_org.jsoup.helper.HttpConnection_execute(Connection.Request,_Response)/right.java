class HttpConnection {
    static Pattern xmlContentTypeRxp;

    static Response execute(Connection.Request req, Response previousResponse) throws IOException {
        Validate.notNull(req, "Request must not be null");
        String protocol = req.url().getProtocol();
        if (!protocol.equals("http") && !protocol.equals("https"))
            throw new MalformedURLException("Only http & https protocols supported");

        if (req.method() == Connection.Method.GET && req.data().size() > 0)
            serialiseRequestUrl(req);
        HttpURLConnection conn = createConnection(req);
        Response res;
        try {
            conn.connect();
            if (req.method() == Connection.Method.POST)
                writePost(req.data(), conn.getOutputStream());

            int status = conn.getResponseCode();
            boolean needsRedirect = false;
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER)
                    needsRedirect = true;
                else if (!req.ignoreHttpErrors())
                    throw new HttpStatusException("HTTP error fetching URL", status, req.url().toString());
            }
            res = new Response(previousResponse);
            res.setupFromConnection(conn, previousResponse);
            if (needsRedirect && req.followRedirects()) {
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
            if (contentType != null
                    && !req.ignoreContentType()
                    && !contentType.startsWith("text/")
                    && !contentType.startsWith("application/xml")
                    && !xmlContentTypeRxp.matcher(contentType).matches()
                    )
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
                res.charset = DataUtil.getCharsetFromContentType(res.contentType);
            } finally {
                if (bodyStream != null) bodyStream.close();
                if (dataStream != null) dataStream.close();
            }
        } finally {
            conn.disconnect();
        }

        res.executed = true;
        return res;
    }
}
