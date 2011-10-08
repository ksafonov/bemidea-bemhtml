package bem.idea.bemhtml;

import bem.idea.bemhtml.highlighter.BemHtmlSyntaxHighlighter;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;

public class BemHtmlLanguage extends Language {

    public BemHtmlLanguage() {
        super("BEMHTML", "text/bemhtml");
        SyntaxHighlighterFactory.LANGUAGE_FACTORY.addExplicitExtension(this, new SingleLazyInstanceSyntaxHighlighterFactory() {
            @NotNull
            protected SyntaxHighlighter createHighlighter() {
                return new BemHtmlSyntaxHighlighter();
            }
        });

    }

    @Override
    public boolean isCaseSensitive() {
        return true;
    }

}
