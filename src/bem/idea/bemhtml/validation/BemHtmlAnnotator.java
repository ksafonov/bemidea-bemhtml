package bem.idea.bemhtml.validation;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

import static bem.idea.bemhtml.lang.lexer.BemHtmlTokenTypes.*;

public class BemHtmlAnnotator extends PsiElementVisitor implements Annotator {

    private AnnotationHolder myHolder;

    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        assert myHolder == null : "unsupported concurrent annotator invocation";
        try {
            myHolder = holder;
            element.accept(this);
        } finally {
            myHolder = null;
        }
    }

    @Override
    public void visitElement(PsiElement element) {
        if (element.getNode().getElementType() == ERROR_NEEDLESS_BEM_VALUE) {
            myHolder.createErrorAnnotation(element, "Needless BEM value");
        }
    }
}
