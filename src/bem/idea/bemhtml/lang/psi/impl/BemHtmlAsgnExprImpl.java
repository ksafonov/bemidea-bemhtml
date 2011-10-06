package bem.idea.bemhtml.lang.psi.impl;

import bem.idea.bemhtml.lang.psi.BemHtmlAsgnExpr;
import com.intellij.lang.ASTNode;
import com.intellij.lang.javascript.psi.impl.JSLiteralExpressionImpl;

public class BemHtmlAsgnExprImpl extends JSLiteralExpressionImpl implements BemHtmlAsgnExpr {

    private boolean firstJs = false;
    private boolean lastJs = false;

    public BemHtmlAsgnExprImpl(ASTNode node) {
        super(node);
    }

    public BemHtmlAsgnExprImpl(ASTNode node, boolean firstJs, boolean lastJs) {
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
