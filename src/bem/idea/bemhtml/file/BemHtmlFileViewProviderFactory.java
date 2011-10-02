package bem.idea.bemhtml.file;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.FileViewProviderFactory;
import com.intellij.psi.PsiManager;

public class BemHtmlFileViewProviderFactory implements FileViewProviderFactory {

    public FileViewProvider createFileViewProvider(VirtualFile file, Language language, PsiManager manager, boolean physical) {
        return new BemHtmlFileViewProvider(manager, file, physical, language);
    }

}
