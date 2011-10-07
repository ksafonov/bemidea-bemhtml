package bem.idea.bemhtml.lang.psi.impl;

import bem.idea.bemhtml.lang.psi.BemHtmlAsgnExpr;
import com.intellij.lang.ASTNode;

public class BemHtmlAsgnExprImpl extends BemHtmlBaseJSElementImpl implements BemHtmlAsgnExpr {

    public BemHtmlAsgnExprImpl(ASTNode node) {
        super(node);
    }

    public BemHtmlAsgnExprImpl(ASTNode node, boolean firstJs, boolean lastJs) {
        super(node, firstJs, lastJs);
    }

}
