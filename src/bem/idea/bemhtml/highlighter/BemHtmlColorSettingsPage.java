package bem.idea.bemhtml.highlighter;

import bem.idea.bemhtml.BemHtmlIcons;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Map;

public class BemHtmlColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] ATTRS = new AttributesDescriptor[] {
            new AttributesDescriptor("Error", BemHtmlSyntaxHighlighter.BAD_CHARACTER),
            new AttributesDescriptor("Keyword: block", BemHtmlSyntaxHighlighter.KEYWORD_BLOCK),
            new AttributesDescriptor("Keyword: elem", BemHtmlSyntaxHighlighter.KEYWORD_ELEM),
            new AttributesDescriptor("Keyword: mod", BemHtmlSyntaxHighlighter.KEYWORD_MOD),
            new AttributesDescriptor("Keyword: elemMod", BemHtmlSyntaxHighlighter.KEYWORD_ELEMMOD),
            new AttributesDescriptor("Keyword: default", BemHtmlSyntaxHighlighter.KEYWORD_DEFAULT),
            new AttributesDescriptor("Keyword: tag", BemHtmlSyntaxHighlighter.KEYWORD_TAG),
            new AttributesDescriptor("Keyword: attrs", BemHtmlSyntaxHighlighter.KEYWORD_ATTRS),
            new AttributesDescriptor("Keyword: cls", BemHtmlSyntaxHighlighter.KEYWORD_CLS),
            new AttributesDescriptor("Keyword: bem", BemHtmlSyntaxHighlighter.KEYWORD_BEM),
            new AttributesDescriptor("Keyword: js", BemHtmlSyntaxHighlighter.KEYWORD_JS),
            new AttributesDescriptor("Keyword: jsAttr", BemHtmlSyntaxHighlighter.KEYWORD_JSATTR),
            new AttributesDescriptor("Keyword: mix", BemHtmlSyntaxHighlighter.KEYWORD_MIX),
            new AttributesDescriptor("Keyword: content", BemHtmlSyntaxHighlighter.KEYWORD_CONTENT),
            new AttributesDescriptor("Keywords delim", BemHtmlSyntaxHighlighter.KEYWORDS_DELIM),
            new AttributesDescriptor("Keywords colon", BemHtmlSyntaxHighlighter.KEYWORDS_COLON),
            new AttributesDescriptor("BEM value", BemHtmlSyntaxHighlighter.BEM_VALUE),
            new AttributesDescriptor("Braces", BemHtmlSyntaxHighlighter.BRACES)
    };

    @NotNull
    public String getDisplayName() {
        return "BemHtml";
    }

    public Icon getIcon() {
        return BemHtmlIcons.FILE_TYPE;
    }

    @NotNull
    public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRS;
    }

    @NotNull
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    public SyntaxHighlighter getHighlighter() {
        return new BemHtmlSyntaxHighlighter();
    }

    @NotNull
    public String getDemoText() {
        return "block b-block, elem myElem, default: {\n" +
                "\n" +
                "    mod pseudo 'y'+'e'+'s' {\n" +
                "        bem: false,\n" +
                "        js: true,\n" +
                "        tag: 'img',\n" +
                "        myProperty: 'text',\n" +
                "        mix: [ this.ctx ]\n" +
                "    }\n" +
                "\n" +
                "    elemMod state current {\n" +
                "        content: {\n" +
                "            tag: 'i',\n" +
                "            elem: 'elem'\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    attrs, this.ctx.url: {\n" +
                "        return { src: this.ctx.url };\n" +
                "    }\n" +
                "\n" +
                "}";
    }

    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }
}
