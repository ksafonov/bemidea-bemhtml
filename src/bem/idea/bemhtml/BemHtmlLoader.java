package bem.idea.bemhtml;

import bem.idea.bemhtml.file.BemHtmlFileType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class BemHtmlLoader implements ApplicationComponent, FileDocumentManagerListener {

    private final static Set<String> touchedFiles = new HashSet<String>();

    public void initComponent() {
        FileDocumentManager.getInstance().addFileDocumentManagerListener(this);
        ApplicationManager.getApplication().runWriteAction(
                new Runnable() {
                    public void run() {
                        FileTypeManager.getInstance().registerFileType(
                                BemHtmlFileType.BEMHTML_FILE_TYPE,
                                BemHtmlFileType.DEFAULT_EXTENSION);
                    }
                }
        );
    }

    public void disposeComponent() {}

    @NotNull
    public String getComponentName() {
        return "BemHtmlLoader";
    }

    public void beforeAllDocumentsSaving() {}

    public void beforeDocumentSaving(Document document) {}

    public void beforeFileContentReload(VirtualFile file, Document document) {}

    public void fileWithNoDocumentChanged(VirtualFile file) {}

    public void fileContentReloaded(VirtualFile file, Document document) {}

    // Hack to force highlighting
    public void fileContentLoaded(VirtualFile file, Document document) {
        if (file.getFileType() == BemHtmlFileType.getFileType() && !touchedFiles.contains(file.getName())) {
            FileDocumentManager.getInstance().reloadFromDisk(document);
            touchedFiles.add(file.getName());
        }
    }

    public void unsavedDocumentsDropped() {}
}
