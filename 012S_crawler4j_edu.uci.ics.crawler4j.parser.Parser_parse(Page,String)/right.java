class Parser {
    void parse_Page_String_() {
                    parseData.setTextContent(new String(page.getContentData()));
                } else {
                    parseData.setTextContent(
                        new String(page.getContentData(), page.getContentCharset()));
                }
                parseData.setOutgoingUrls(page.getWebURL());
                page.setParseData(parseData);
            } catch (Exception e) {
                logger.error("{}, while parsing css: {}", e.getMessage(), page.getWebURL().getURL());
                throw new ParseException();
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
                parseData.setOutgoingUrls(Net.extractUrls(parseData.getTextContent()));
                page.setParseData(parseData);
            } catch (Exception e) {
                logger.error("{}, while parsing: {}", e.getMessage(), page.getWebURL().getURL());
                throw new ParseException();
            }
        } else {

            HtmlParseData parsedData = this.htmlContentParser.parse(page, contextURL);

            if (page.getContentCharset() == null) {
                page.setContentCharset(parsedData.getContentCharset());
            }

            LanguageIdentifier languageIdentifier = new LanguageIdentifier(parsedData.getText());
            page.setLanguage(languageIdentifier.getLanguage());

            page.setParseData(parsedData);

        }
    }
    }
}
