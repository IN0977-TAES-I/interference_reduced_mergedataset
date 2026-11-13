// class name : edu.uci.ics.crawler4j.parser.Parser
// method name: parse(Page,String)
// side       : left
// region     : lines 72-113 (affected lines: [72, 73, 75, 85, 89, 91, 92, 93, 95, 96, 98, 99, 101, 102, 113])
class Parser {
    void parse_Page_String_() {
   72:                         if (config.isHaltOnError()) { <== AFFECTED
   73:                             throw new ParseException(e); <== AFFECTED
   74:                         } else {
   75:                             logger.error("Error parsing file", e); <== AFFECTED
   76:                         }
   77:                     }
   78:                 } else {
   79:                     parseData.setHtml("<html></html>");
   80:                 }
   81:                 page.setParseData(parseData);
   82:                 if (parseData.getHtml() == null) {
   83:                     throw new ParseException();
   84:                 }
   85:                 parseData.setOutgoingUrls(net.extractUrls(parseData.getHtml())); <== AFFECTED
   86:             } else {
   87:                 throw new NotAllowedContentException();
   88:             }
   89:         } else if (Util.hasPlainTextContent(page.getContentType())) { // plain Text <== AFFECTED
   90:             try {
   91:                 TextParseData parseData = new TextParseData(); <== AFFECTED
   92:                 if (page.getContentCharset() == null) { <== AFFECTED
   93:                     parseData.setTextContent(new String(page.getContentData())); <== AFFECTED
   94:                 } else {
   95:                     parseData.setTextContent( <== AFFECTED
   96:                         new String(page.getContentData(), page.getContentCharset())); <== AFFECTED
   97:                 }
   98:                 parseData.setOutgoingUrls(net.extractUrls(parseData.getTextContent())); <== AFFECTED
   99:                 page.setParseData(parseData); <== AFFECTED
  100:             } catch (Exception e) {
  101:                 logger.error("{}, while parsing: {}", e.getMessage(), page.getWebURL().getURL()); <== AFFECTED
  102:                 throw new ParseException(e); <== AFFECTED
  103:             }
  104:         } else { // isHTML
  105: 
  106:             HtmlParseData parsedData = this.htmlContentParser.parse(page, contextURL);
  107: 
  108:             if (page.getContentCharset() == null) {
  109:                 page.setContentCharset(parsedData.getContentCharset());
  110:             }
  111: 
  112:             // Please note that identifying language takes less than 10 milliseconds
  113:             LanguageIdentifier languageIdentifier = new LanguageIdentifier(parsedData.getText()); <== AFFECTED
    }
}
