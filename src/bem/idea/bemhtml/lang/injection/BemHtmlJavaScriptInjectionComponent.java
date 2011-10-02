package bem.idea.bemhtml.lang.injection;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.psi.LanguageInjector;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class BemHtmlJavaScriptInjectionComponent implements ProjectComponent {

    PsiManager psiman;

    LanguageInjector inj = new BemHtmlJavaScriptInjector();

    public BemHtmlJavaScriptInjectionComponent(PsiManager psiman) {
        this.psiman = psiman;
    }

    public void projectOpened() {
        psiman.registerLanguageInjector(inj);
    }

    public void projectClosed() {
        psiman.unregisterLanguageInjector(inj);
    }

    @NonNls
    @NotNull
    public String getComponentName() {
        return "BemHtml.JavaScriptInjection";
    }

    public void initComponent() {}

    public void disposeComponent() {}

}