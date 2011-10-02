package bem.idea.bemhtml.lang.lexer;

import com.intellij.psi.tree.TokenSet;

public class BemHtmlTokenSets implements BemHtmlTokenTypes {

    public static TokenSet WHITESPACE_TOKEN_SET = TokenSet.create(
            WHITE_SPACE
    );

    public static TokenSet COMMENTS_TOKEN_SET = TokenSet.create();

    public static TokenSet STRING_TOKEN_SET = TokenSet.create();

}
