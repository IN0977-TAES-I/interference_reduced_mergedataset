class HttpConnection {
    void execute_Connection_Request__Response_() {
                    if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER || status == HTTP_TEMP_REDIR)
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
    }
}
