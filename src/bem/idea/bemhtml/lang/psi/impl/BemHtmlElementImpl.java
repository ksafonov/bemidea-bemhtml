package bem.idea.bemhtml.lang.psi.impl;

import bem.idea.bemhtml.BemHtmlLanguage;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import bem.idea.bemhtml.lang.psi.BemHtmlElement;
import com.intellij.lang.Language;
import org.jetbrains.annotations.NotNull;

public class BemHtmlElementImpl extends ASTWrapperPsiElement implements BemHtmlElement {

    private final static Language language = new BemHtmlLanguage();
    private final ASTNode node;

    public BemHtmlElementImpl(final ASTNode node) {
        super(node);
        this.node = node;
    }

    @NotNull
    public Language getLanguage() {
        return language;
    }

    public String toString() {
        return "BEMHTML:" + node.getElementType().toString();
    }

}
