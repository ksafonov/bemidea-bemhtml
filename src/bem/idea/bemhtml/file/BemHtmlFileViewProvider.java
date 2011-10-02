package bem.idea.bemhtml.file;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.annotations.NotNull;

public class BemHtmlFileViewProvider extends SingleRootFileViewProvider {

    public BemHtmlFileViewProvider(@NotNull PsiManager manager, @NotNull VirtualFile file) {
        super(manager, file);
    }

    public BemHtmlFileViewProvider(@NotNull PsiManager manager, @NotNull VirtualFile virtualFile, final boolean physical) {
        super(manager, virtualFile, physical);
    }

    protected BemHtmlFileViewProvider(@NotNull PsiManager manager, @NotNull VirtualFile virtualFile, final boolean physical, @NotNull Language language) {
        super(manager, virtualFile, physical, language);
    }

    public boolean supportsIncrementalReparse(final Language rootLanguage) {
        return true;
    }

}
