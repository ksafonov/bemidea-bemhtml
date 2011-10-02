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
                first == BemHtmlTokenTypes.KEYWORDS_VALUE ||
                first == BemHtmlTokenTypes.BEM_VALUE) {
            parseJavaScript(builder, first);
        } else {
            builder.advanceLexer();
        }
    }

    private void parseJavaScript(PsiBuilder builder, IElementType type) {
        PsiBuilder.Marker marker = builder.mark();
        builder.advanceLexer();
        marker.done(type);
    }

}
