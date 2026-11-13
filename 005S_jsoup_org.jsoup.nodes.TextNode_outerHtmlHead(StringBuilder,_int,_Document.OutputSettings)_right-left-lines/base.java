// class name : org.jsoup.nodes.TextNode
// method name: outerHtmlHead(StringBuilder, int, Document.OutputSettings)
// side       : base
// region     : lines 94-98 (affected lines: [94, 98])
class TextNode {
    void outerHtmlHead_StringBuilder__int__Document_OutputSettings_() {
   94:         if (out.prettyPrint() && parent() instanceof Element && !((Element) parent()).preserveWhitespace()) { <== AFFECTED
   95:             html = normaliseWhitespace(html);
   96:         }
   97: 
   98:         if (out.prettyPrint() && siblingIndex() == 0 && parentNode instanceof Element && ((Element) parentNode).tag().formatAsBlock() && !isBlank()) <== AFFECTED
    }
}
