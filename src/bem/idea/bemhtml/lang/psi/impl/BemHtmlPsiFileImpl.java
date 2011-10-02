package bem.idea.bemhtml.lang.psi.impl;

import bem.idea.bemhtml.lang.psi.BemHtmlPsiFile;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import static bem.idea.bemhtml.file.BemHtmlFileType.*;

public class BemHtmlPsiFileImpl extends PsiFileBase implements BemHtmlPsiFile {

    public BemHtmlPsiFileImpl(FileViewProvider viewProvider) {
        super(viewProvider, BEMHTML_FILE_TYPE.getLanguage());
    }

    @NotNull
    public FileType getFileType() {
        return BEMHTML_FILE_TYPE;
    }

    public String toString() {
        return "BEMHTML File: " + getName();
    }

}