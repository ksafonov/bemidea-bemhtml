package bem.idea.bemhtml.lang.lexer;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class BemHtmlFlexLexer extends FlexAdapter {

    public BemHtmlFlexLexer() {
        super(new BemHtmlLexer((Reader)null));
    }

}
