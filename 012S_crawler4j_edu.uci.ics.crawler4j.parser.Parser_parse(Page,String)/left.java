class Parser {
    void parse_Page_String_() {
                        if (config.isHaltOnError()) {
                            throw new ParseException(e);
                        } else {
                            logger.error("Error parsing file", e);
                        }
                    }
                } else {
                    parseData.setHtml("<html></html>");
                }
                page.setParseData(parseData);
                if (parseData.getHtml() == null) {
                    throw new ParseException();
                }
                parseData.setOutgoingUrls(net.extractUrls(parseData.getHtml()));
            } else {
                throw new NotAllowedContentException();
            }
        } else if (Util.hasPlainTextContent(page.getContentType())) {
            try {
                TextParseData parseData = new TextParseData();
                if (page.getContentCharset() == null) {
                    parseData.setTextContent(new String(page.getContentData()));
                } else {
                    parseData.setTextContent(
                        new String(page.getContentData(), page.getContentCharset()));
                }
                parseData.setOutgoingUrls(net.extractUrls(parseData.getTextContent()));
                page.setParseData(parseData);
            } catch (Exception e) {
                logger.error("{}, while parsing: {}", e.getMessage(), page.getWebURL().getURL());
                throw new ParseException(e);
            }
        } else {

            HtmlParseData parsedData = this.htmlContentParser.parse(page, contextURL);

            if (page.getContentCharset() == null) {
                page.setContentCharset(parsedData.getContentCharset());
            }

            LanguageIdentifier languageIdentifier = new LanguageIdentifier(parsedData.getText());
    }
}
