package bem.idea.bemhtml.file;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileTypes.FileTypeManager;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class BemHtmlFileTypeManager implements ApplicationComponent {

    public static BemHtmlFileTypeManager getInstance() {
        return ApplicationManager.getApplication().getComponent(BemHtmlFileTypeManager.class);
    }

    private final BemHtmlFileType fileType = new BemHtmlFileType();

    @NotNull
    @NonNls
    public String getComponentName() {
        return "BemHtmlFileTypeManager";
    }

    public void initComponent() {
        FileTypeManager.getInstance().registerFileType(fileType, BemHtmlFileType.DEFAULT_EXTENSION);
    }

    public void disposeComponent() {
    }

    public final BemHtmlFileType getFileType() {
        return fileType;
    }

}
