// class name : edu.uci.ics.crawler4j.parser.Parser
// method name: parse(Page,String)
// side       : base
// region     : lines 72-113 (affected lines: [72, 73, 75, 85, 89, 91, 92, 93, 95, 96, 98, 99, 101, 102, 113])
class Parser {
    void parse_Page_String_() {
   72:                     parseData.setTextContent(new String(page.getContentData())); <== AFFECTED
   73:                 } else { <== AFFECTED
   74:                     parseData.setTextContent(
   75:                         new String(page.getContentData(), page.getContentCharset())); <== AFFECTED
   76:                 }
   77:                 parseData.setOutgoingUrls(Net.extractUrls(parseData.getTextContent()));
   78:                 page.setParseData(parseData);
   79:             } catch (Exception e) {
   80:                 logger.error("{}, while parsing: {}", e.getMessage(), page.getWebURL().getURL());
   81:                 throw new ParseException();
   82:             }
   83:         } else { // isHTML
   84: 
   85:             HtmlParseData parsedData = this.htmlContentParser.parse(page, contextURL); <== AFFECTED
   86: 
   87:             if (page.getContentCharset() == null) {
   88:                 page.setContentCharset(parsedData.getContentCharset());
   89:             } <== AFFECTED
   90: 
   91:             // Please note that identifying language takes less than 10 milliseconds <== AFFECTED
   92:             LanguageIdentifier languageIdentifier = new LanguageIdentifier(parsedData.getText()); <== AFFECTED
   93:             page.setLanguage(languageIdentifier.getLanguage()); <== AFFECTED
   94: 
   95:             page.setParseData(parsedData); <== AFFECTED
   96:  <== AFFECTED
   97:         }
   98:     } <== AFFECTED
   99: } <== AFFECTED
    }
}
