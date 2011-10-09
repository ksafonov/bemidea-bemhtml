package bem.idea.bemhtml;

import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.PsiModificationTrackerImpl;
import org.jetbrains.annotations.NotNull;

public class BemHtmlProjectComponent extends AbstractProjectComponent implements DocumentListener {

    private Project myProject = null;

    protected BemHtmlProjectComponent(Project project) {
        super(project);
        myProject = project;
    }

    public void initComponent() {
        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(this);
    }

    public void disposeComponent() {
        EditorFactory.getInstance().getEventMulticaster().removeDocumentListener(this);
    }

    public void beforeDocumentChange(DocumentEvent event) {}

    public void documentChanged(DocumentEvent event) {
        ((PsiModificationTrackerImpl)PsiManager.getInstance(myProject).getModificationTracker()).incOutOfCodeBlockModificationCounter();
    }

    public void projectOpened() {}

    public void projectClosed() {}

    @NotNull
    public String getComponentName() {
        return "BemHtmlProjectComponent";
    }

}
