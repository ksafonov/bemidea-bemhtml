package bem.idea.bemhtml.highlighter;

import bem.idea.bemhtml.lang.lexer.BemHtmlHighlighterLexer;
import bem.idea.bemhtml.lang.lexer.BemHtmlTokenTypes;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BemHtmlSyntaxHighlighter extends SyntaxHighlighterBase {

    private final BemHtmlHighlighterLexer lexer = new BemHtmlHighlighterLexer();

    private static final TextAttributes taBemKeyword = new TextAttributes(new Color(0, 0, 139), null, null, null, Font.BOLD);
    private static final TextAttributes taBemValue = new TextAttributes(new Color(69, 139, 0), null, null, null, Font.BOLD);
    private static final TextAttributes taJSONProperty = new TextAttributes(new Color(0, 0, 139), null, null, null, Font.PLAIN);
    private static final TextAttributes taJSBraces = new TextAttributes(new Color(0, 0, 0), null, null, null, Font.PLAIN);
    private static final TextAttributes taBemKeywordsDelim = new TextAttributes(new Color(0, 0, 0), null, null, null, Font.PLAIN);
    private static final TextAttributes taBemKeywordsColon = new TextAttributes(new Color(0, 0, 0), null, null, null, Font.PLAIN);
    private static final TextAttributes taBemError = new TextAttributes(new Color(0, 0, 0), null, new Color(255, 0, 0), EffectType.WAVE_UNDERSCORE, Font.PLAIN);

    private static final Map<IElementType, TextAttributesKey> TOKENS_TO_STYLES;

    @NotNull
    public Lexer getHighlightingLexer() {
        return lexer;
    }

    static final TextAttributesKey BAD_CHARACTER = TextAttributesKey.createTextAttributesKey(
            "bemhtml.bem.error", taBemError);

    static final TextAttributesKey KEYWORDS_DELIM = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keywords.delim", taBemKeywordsDelim);

    static final TextAttributesKey KEYWORDS_COLON = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keywords.colon", taBemKeywordsColon);

    static final TextAttributesKey BEM_VALUE = TextAttributesKey.createTextAttributesKey(
            "bemhtml.bem.value", taBemValue);

    static final TextAttributesKey JSON_PROPERTY = TextAttributesKey.createTextAttributesKey(
            "bemhtml.json.property", taJSONProperty);

    static final TextAttributesKey KEYWORD_BLOCK = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.block", taBemKeyword);

    static final TextAttributesKey KEYWORD_ELEM = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.elem", taBemKeyword);

    static final TextAttributesKey KEYWORD_MOD = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.mod", taBemKeyword);

    static final TextAttributesKey KEYWORD_ELEMMOD = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.elemmod", taBemKeyword);

    static final TextAttributesKey KEYWORD_DEFAULT = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.default", taBemKeyword);

    static final TextAttributesKey KEYWORD_TAG = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.tag", taBemKeyword);

    static final TextAttributesKey KEYWORD_ATTRS = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.attrs", taBemKeyword);

    static final TextAttributesKey KEYWORD_CLS = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.cls", taBemKeyword);

    static final TextAttributesKey KEYWORD_BEM = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.bem", taBemKeyword);

    static final TextAttributesKey KEYWORD_JS = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.js", taBemKeyword);

    static final TextAttributesKey KEYWORD_JSATTR = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.jsattr", taBemKeyword);

    static final TextAttributesKey KEYWORD_MIX = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.mix", taBemKeyword);

    static final TextAttributesKey KEYWORD_CONTENT = TextAttributesKey.createTextAttributesKey(
            "bemhtml.keyword.content", taBemKeyword);

    static final TextAttributesKey BRACES = TextAttributesKey.createTextAttributesKey(
            "bemhtml.braces", taJSBraces);

    static {
        TOKENS_TO_STYLES = new HashMap<IElementType, TextAttributesKey>();

        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.WHITE_SPACE, HighlighterColors.TEXT);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.BAD_CHARACTER, BAD_CHARACTER);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORDS_DELIM, KEYWORDS_DELIM);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORDS_COLON, KEYWORDS_COLON);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.BEM_VALUE, BEM_VALUE);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.JSON_PROPERTY, JSON_PROPERTY);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_BLOCK, KEYWORD_BLOCK);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_ELEM, KEYWORD_ELEM);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_MOD, KEYWORD_MOD);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_ELEMMOD, KEYWORD_ELEMMOD);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_DEFAULT, KEYWORD_DEFAULT);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_TAG, KEYWORD_TAG);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_ATTRS, KEYWORD_ATTRS);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_CLS, KEYWORD_CLS);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_BEM, KEYWORD_BEM);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_JS, KEYWORD_JS);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_JSATTR, KEYWORD_JSATTR);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_MIX, KEYWORD_MIX);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.KEYWORD_CONTENT, KEYWORD_CONTENT);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.LEFT_BRACE, BRACES);
        TOKENS_TO_STYLES.put(BemHtmlTokenTypes.RIGHT_BRACE, BRACES);
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(TOKENS_TO_STYLES.get(tokenType));
    }

}
