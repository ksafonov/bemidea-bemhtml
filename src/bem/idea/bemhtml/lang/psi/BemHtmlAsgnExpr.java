package bem.idea.bemhtml.lang.psi;

import com.intellij.psi.PsiLanguageInjectionHost;

public interface BemHtmlAsgnExpr extends BemHtmlElement, PsiLanguageInjectionHost {

    boolean isFirstJs();
    boolean isLastJs();

}
