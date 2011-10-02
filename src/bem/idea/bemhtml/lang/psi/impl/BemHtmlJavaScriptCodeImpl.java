package bem.idea.bemhtml.lang.psi.impl;

import bem.idea.bemhtml.lang.psi.BemHtmlJavaScriptCode;
import com.intellij.lang.ASTNode;
import com.intellij.lang.javascript.psi.impl.JSLiteralExpressionImpl;

public class BemHtmlJavaScriptCodeImpl extends JSLiteralExpressionImpl implements BemHtmlJavaScriptCode {

    public BemHtmlJavaScriptCodeImpl(ASTNode node) {
        super(node);
    }

}