class TextNode {
    void outerHtmlHead_StringBuilder__int__Document_OutputSettings_() {
        if (out.prettyPrint() && parent() instanceof Element && !Element.preserveWhitespace((Element) parent())) {
            html = normaliseWhitespace(html);
        }

        if (out.prettyPrint() && siblingIndex() == 0 && parentNode instanceof Element && ((Element) parentNode).tag().formatAsBlock() && !isBlank())
    }
}
