class TextNode {
    void outerHtmlHead(StringBuilder accum, int depth, Document.OutputSettings out) {
        String html = Entities.escape(getWholeText(), out);
        if (out.prettyPrint() && parent() instanceof Element && !Element.preserveWhitespace((Element) parent())) {
            html = normaliseWhitespace(html);
        }

        if (out.prettyPrint() && siblingIndex() == 0 && parentNode instanceof Element && ((Element) parentNode).tag().formatAsBlock() && !isBlank())
            indent(accum, depth, out);
        accum.append(html);
    }
}
