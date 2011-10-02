package bem.idea.bemhtml.highlighter;

import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;

public class BemHtmlHighlighterFactory extends SingleLazyInstanceSyntaxHighlighterFactory {

    @NotNull
    protected BemHtmlSyntaxHighlighter createHighlighter() {
        return new BemHtmlSyntaxHighlighter();
    }

}
