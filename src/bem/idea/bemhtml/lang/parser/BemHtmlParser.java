package bem.idea.bemhtml.lang.parser;

import bem.idea.bemhtml.lang.lexer.BemHtmlTokenTypes;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class BemHtmlParser implements PsiParser {

    @NotNull
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        final PsiBuilder.Marker rootMarker = builder.mark();

        while (!builder.eof()) {
            parse(builder);
        }
        rootMarker.done(root);

        return builder.getTreeBuilt();
    }

    private void parse(PsiBuilder builder) {
        IElementType first = builder.getTokenType();
        if (first == BemHtmlTokenTypes.JAVASCRIPT_CODE ||
                first == BemHtmlTokenTypes.FIRST_JAVASCRIPT_CODE ||
                first == BemHtmlTokenTypes.LAST_JAVASCRIPT_CODE ||
                first == BemHtmlTokenTypes.SINGLE_JAVASCRIPT_CODE ||
                first == BemHtmlTokenTypes.KEYWORDS_VALUE ||
                first == BemHtmlTokenTypes.KEYWORDS_DELIM ||
                first == BemHtmlTokenTypes.KEYWORDS_COLON ||
                first == BemHtmlTokenTypes.BEM_VALUE ||
                first == BemHtmlTokenTypes.JS_EXPRESSION ||
                first == BemHtmlTokenTypes.FIRST_JS_EXPRESSION ||
                first == BemHtmlTokenTypes.LAST_JS_EXPRESSION ||
                first == BemHtmlTokenTypes.SINGLE_JS_EXPRESSION ||
                first == BemHtmlTokenTypes.JSON_PROPERTY ||
                first == BemHtmlTokenTypes.WHITE_SPACE ||
                first == BemHtmlTokenTypes.KEYWORD_ATTRS ||
                first == BemHtmlTokenTypes.KEYWORD_BEM ||
                first == BemHtmlTokenTypes.KEYWORD_BLOCK ||
                first == BemHtmlTokenTypes.KEYWORD_CLS ||
                first == BemHtmlTokenTypes.KEYWORD_CONTENT ||
                first == BemHtmlTokenTypes.KEYWORD_DEFAULT ||
                first == BemHtmlTokenTypes.KEYWORD_ELEM ||
                first == BemHtmlTokenTypes.KEYWORD_ELEMMOD ||
                first == BemHtmlTokenTypes.KEYWORD_JS ||
                first == BemHtmlTokenTypes.KEYWORD_JSATTR ||
                first == BemHtmlTokenTypes.KEYWORD_MIX ||
                first == BemHtmlTokenTypes.KEYWORD_MOD ||
                first == BemHtmlTokenTypes.KEYWORD_TAG) {
            parseToken(builder, first);
        } else {
            builder.advanceLexer();
        }
    }

    private void parseToken(PsiBuilder builder, IElementType type) {
        PsiBuilder.Marker marker = builder.mark();
        builder.advanceLexer();
        marker.done(type);
    }

}
