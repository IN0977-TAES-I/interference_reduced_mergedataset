// class name : edu.uci.ics.crawler4j.parser.Parser
// method name: parse(Page,String)
// side       : right
// region     : lines 72-113 (affected lines: [72, 73, 75, 85, 89, 91, 92, 93, 95, 96, 98, 99, 101, 102, 113])
class Parser {
    void parse_Page_String_() {
   72:                     parseData.setTextContent(new String(page.getContentData())); <== AFFECTED
   73:                 } else { <== AFFECTED
   74:                     parseData.setTextContent(
   75:                         new String(page.getContentData(), page.getContentCharset())); <== AFFECTED
   76:                 }
   77:                 parseData.setOutgoingUrls(page.getWebURL());
   78:                 page.setParseData(parseData);
   79:             } catch (Exception e) {
   80:                 logger.error("{}, while parsing css: {}", e.getMessage(), page.getWebURL().getURL());
   81:                 throw new ParseException();
   82:             }
   83:         } else if (Util.hasPlainTextContent(page.getContentType())) { // plain Text
   84:             try {
   85:                 TextParseData parseData = new TextParseData(); <== AFFECTED
   86:                 if (page.getContentCharset() == null) {
   87:                     parseData.setTextContent(new String(page.getContentData()));
   88:                 } else {
   89:                     parseData.setTextContent( <== AFFECTED
   90:                         new String(page.getContentData(), page.getContentCharset()));
   91:                 } <== AFFECTED
   92:                 parseData.setOutgoingUrls(Net.extractUrls(parseData.getTextContent())); <== AFFECTED
   93:                 page.setParseData(parseData); <== AFFECTED
   94:             } catch (Exception e) {
   95:                 logger.error("{}, while parsing: {}", e.getMessage(), page.getWebURL().getURL()); <== AFFECTED
   96:                 throw new ParseException(); <== AFFECTED
   97:             }
   98:         } else { // isHTML <== AFFECTED
   99:  <== AFFECTED
  100:             HtmlParseData parsedData = this.htmlContentParser.parse(page, contextURL);
  101:  <== AFFECTED
  102:             if (page.getContentCharset() == null) { <== AFFECTED
  103:                 page.setContentCharset(parsedData.getContentCharset());
  104:             }
  105: 
  106:             // Please note that identifying language takes less than 10 milliseconds
  107:             LanguageIdentifier languageIdentifier = new LanguageIdentifier(parsedData.getText());
  108:             page.setLanguage(languageIdentifier.getLanguage());
  109: 
  110:             page.setParseData(parsedData);
  111: 
  112:         }
  113:     } <== AFFECTED
    }
}
