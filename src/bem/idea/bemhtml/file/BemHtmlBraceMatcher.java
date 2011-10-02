package bem.idea.bemhtml.file;

import bem.idea.bemhtml.lang.lexer.BemHtmlTokenTypes;
import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BemHtmlBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(BemHtmlTokenTypes.LEFT_BRACE, BemHtmlTokenTypes.RIGHT_BRACE, true)
    };

    public BracePair[] getPairs() {
        return PAIRS;
    }

    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return (BemHtmlTokenTypes.RIGHT_BRACE == contextType ||
                BemHtmlTokenTypes.WHITE_SPACE == contextType ||
                BemHtmlTokenTypes.KEYWORDS_COLON == contextType ||
                contextType == null
        );
    }

    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        PsiElement element = file.findElementAt(openingBraceOffset);

        if ((element == null) || (element instanceof PsiFile)) return openingBraceOffset;

        return element.getParent().getTextRange().getStartOffset();
    }
}
