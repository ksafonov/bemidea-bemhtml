package bem.idea.bemhtml;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

public class BemHtmlLoader implements ApplicationComponent {

    public void initComponent() {}

    public void disposeComponent() {}

    @NotNull
    public String getComponentName() {
        return "BemHtmlLoader";
    }
}
