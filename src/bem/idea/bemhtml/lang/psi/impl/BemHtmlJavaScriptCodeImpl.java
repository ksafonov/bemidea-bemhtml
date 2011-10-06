package bem.idea.bemhtml.lang.psi.impl;

import bem.idea.bemhtml.lang.psi.BemHtmlJavaScriptCode;
import com.intellij.lang.ASTNode;
import com.intellij.lang.javascript.psi.impl.JSLiteralExpressionImpl;

public class BemHtmlJavaScriptCodeImpl extends JSLiteralExpressionImpl implements BemHtmlJavaScriptCode {

    private boolean firstJs = false;
    private boolean lastJs = false;

    public BemHtmlJavaScriptCodeImpl(ASTNode node) {
        super(node);
    }

    public BemHtmlJavaScriptCodeImpl(ASTNode node, boolean firstJs, boolean lastJs) {
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

}