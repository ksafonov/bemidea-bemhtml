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
        if (element.getNode().getElementType() == ERROR_TOO_MANY_VALUES) {
            myHolder.createErrorAnnotation(element, "Unexpected BEM value / JavaScript expression");
        } else if (element.getNode().getElementType() == ERROR_WHITESPACE_EXPECTED) {
            myHolder.createErrorAnnotation(element, "Whitespace expected");
        } else if (element.getNode().getElementType() == ERROR_ONE_BEM_VALUE_EXPECTED) {
            myHolder.createErrorAnnotation(element, "One BEM value / JavaScript expression expected");
        } else if (element.getNode().getElementType() == ERROR_TWO_BEM_VALUES_EXPECTED) {
            myHolder.createErrorAnnotation(element, "Two BEM values / JavaScript expressions expected");
        }
    }
}
