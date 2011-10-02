package bem.idea.bemhtml.lang.psi.impl;

import bem.idea.bemhtml.lang.psi.BemHtmlBemValue;
import com.intellij.lang.ASTNode;
import com.intellij.lang.javascript.psi.impl.JSLiteralExpressionImpl;

public class BemHtmlBemValueImpl extends JSLiteralExpressionImpl implements BemHtmlBemValue {

    public BemHtmlBemValueImpl(ASTNode node) {
        super(node);
    }

}
