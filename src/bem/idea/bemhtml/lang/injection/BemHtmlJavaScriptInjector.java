package bem.idea.bemhtml.lang.injection;

import bem.idea.bemhtml.lang.psi.BemHtmlAsgnExpr;
import bem.idea.bemhtml.lang.psi.BemHtmlJavaScriptCode;
import com.intellij.lang.Language;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

public class BemHtmlJavaScriptInjector implements LanguageInjector {

    private final static String mainPrefix =
            "function ___BEMHTMLPLUGIN___() {" +
            "this.block=1;" +
            "this.elem=1;" +
            "this.mods=1;" +
            "this.elemMods=1;" +
            "this.ctx=1;" +
            "this.isFirst=1;" +
            "this.isLast=1;" +
            "this.position=1;";

    private final static String asgnPrefix =
            mainPrefix + "return (";

    private final static String asgnSuffix =
            ")}";

    private final static String jsPrefix =
            asgnPrefix + "function() {";

    private final static String jsSuffix =
            "})}";

    public void getLanguagesToInject(@NotNull PsiLanguageInjectionHost _host,
                                     @NotNull InjectedLanguagePlaces registrar) {
        if (_host instanceof BemHtmlJavaScriptCode) {
            BemHtmlJavaScriptCode host = (BemHtmlJavaScriptCode)_host;
            registrar.addPlace(Language.findLanguageByID("JavaScript"),
                    new TextRange(0, host.getTextLength()), jsPrefix, jsSuffix);
        } else if (_host instanceof BemHtmlAsgnExpr) {
            BemHtmlAsgnExpr host = (BemHtmlAsgnExpr)_host;
            registrar.addPlace(Language.findLanguageByID("JavaScript"),
                    new TextRange(0, host.getTextLength()), asgnPrefix, asgnSuffix);
        }
    }

}
