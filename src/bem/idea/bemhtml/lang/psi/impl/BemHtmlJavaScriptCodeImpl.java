package bem.idea.bemhtml.lang.psi.impl;

import bem.idea.bemhtml.lang.psi.BemHtmlJavaScriptCode;
import com.intellij.lang.ASTNode;

public class BemHtmlJavaScriptCodeImpl extends BemHtmlBaseJSElementImpl implements BemHtmlJavaScriptCode {

    public BemHtmlJavaScriptCodeImpl(ASTNode node) {
        super(node);
    }

    public BemHtmlJavaScriptCodeImpl(ASTNode node, boolean firstJs, boolean lastJs) {
        super(node, firstJs, lastJs);
    }

}