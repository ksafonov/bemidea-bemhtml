package bem.idea.bemhtml.lang.psi.impl;

import bem.idea.bemhtml.lang.psi.BemHtmlAsgnExpr;
import com.intellij.lang.ASTNode;
import com.intellij.lang.javascript.psi.impl.JSLiteralExpressionImpl;

public class BemHtmlAsgnExprImpl extends JSLiteralExpressionImpl implements BemHtmlAsgnExpr {

    public BemHtmlAsgnExprImpl(ASTNode node) {
        super(node);
    }

}
