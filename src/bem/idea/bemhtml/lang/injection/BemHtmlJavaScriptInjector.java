package bem.idea.bemhtml.lang.injection;

import bem.idea.bemhtml.lang.psi.BemHtmlAsgnExpr;
import bem.idea.bemhtml.lang.psi.BemHtmlBemValue;
import bem.idea.bemhtml.lang.psi.BemHtmlJavaScriptCode;
import com.intellij.lang.Language;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

public class BemHtmlJavaScriptInjector implements LanguageInjector {

    public void getLanguagesToInject(@NotNull PsiLanguageInjectionHost _host,
                                     @NotNull InjectedLanguagePlaces registrar) {
        if (_host instanceof BemHtmlJavaScriptCode) {
            BemHtmlJavaScriptCode host = (BemHtmlJavaScriptCode)_host;
            registrar.addPlace(Language.findLanguageByID("JavaScript"),
                            new TextRange(0, host.getTextLength()), null, null);
        } else if (_host instanceof BemHtmlAsgnExpr) {
            BemHtmlAsgnExpr host = (BemHtmlAsgnExpr)_host;
            registrar.addPlace(Language.findLanguageByID("JavaScript"),
                            new TextRange(0, host.getTextLength()), "function ___BEMHTMLPLUGIN___() {return ", "}");
        } else if (_host instanceof BemHtmlBemValue) {
            BemHtmlBemValue host = (BemHtmlBemValue)_host;
            if (!host.getText().matches("^[\\w\\-]+$")) {
                registrar.addPlace(Language.findLanguageByID("JavaScript"),
                                new TextRange(0, host.getTextLength()), "function ___BEMHTMLPLUGIN___() {return ", "}");
            }
        }
    }

}
