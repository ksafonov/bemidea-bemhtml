package bem.idea.bemhtml.lang.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.tree.TokenSet;

public class BemHtmlMergingLexer extends MergingLexerAdapter {

    public static final TokenSet TOKENS_TO_MERGE = TokenSet.create(
            BemHtmlTokenTypes.JAVASCRIPT_CODE,
            BemHtmlTokenTypes.BAD_CHARACTER,
            BemHtmlTokenTypes.KEYWORDS_VALUE,
            BemHtmlTokenTypes.BEM_VALUE,
            BemHtmlTokenTypes.WHITE_SPACE
    );

    public BemHtmlMergingLexer() {
        super(new BemHtmlFlexLexer(), TOKENS_TO_MERGE);
    }

}
