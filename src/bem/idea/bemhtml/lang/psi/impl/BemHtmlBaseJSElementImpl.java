package bem.idea.bemhtml.lang.psi.impl;

import bem.idea.bemhtml.lang.psi.BemHtmlFirstLastElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.LiteralTextEscaper;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import com.intellij.psi.impl.source.tree.injected.StringLiteralEscaper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BemHtmlBaseJSElementImpl extends BemHtmlElementImpl implements PsiLanguageInjectionHost, BemHtmlFirstLastElement {

    private boolean firstJs = false;
    private boolean lastJs = false;

    public BemHtmlBaseJSElementImpl(ASTNode node) {
        super(node);
    }

    public BemHtmlBaseJSElementImpl(ASTNode node, boolean firstJs, boolean lastJs) {
        this(node);
        this.firstJs = firstJs;
        this.lastJs = lastJs;
    }

    public boolean isFirstJs() {
        return firstJs;
    }

    public boolean isLastJs() {
        return lastJs;
    }

    public List<Pair<PsiElement, TextRange>> getInjectedPsi() {
        return InjectedLanguageUtil.getInjectedPsiFiles(this);
    }

    public void processInjectedPsi(@NotNull PsiLanguageInjectionHost.InjectedPsiVisitor visitor) {
        InjectedLanguageUtil.enumerate(this, visitor);
    }

    public PsiLanguageInjectionHost updateText(@NotNull String text) {
        ASTNode valueNode = getNode().getFirstChildNode();
        ((LeafElement)valueNode).replaceWithText(text);
        return this;
    }

    @NotNull
    public LiteralTextEscaper<? extends PsiLanguageInjectionHost> createLiteralTextEscaper() {
        return new StringLiteralEscaper(this);
    }

}
