package bem.idea.bemhtml.file;

import bem.idea.bemhtml.BemHtmlIcons;
import bem.idea.bemhtml.BemHtmlLanguage;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class BemHtmlFileType extends LanguageFileType {

    public static final BemHtmlFileType BEMHTML_FILE_TYPE = new BemHtmlFileType();
    public static final Language BEMHTML_LANGUAGE = BEMHTML_FILE_TYPE.getLanguage();

    public static final String DEFAULT_EXTENSION = "bemhtml";

    public BemHtmlFileType() {
        super(new BemHtmlLanguage());
    }

    @NotNull
    public String getName() {
        return "BEMHTML";
    }

    @NotNull
    public String getDescription() {
        return "BEMHTML Files";
    }

    @NotNull
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    public Icon getIcon() {
        return BemHtmlIcons.FILE_TYPE;
    }

    public static FileType getFileType() {
        return BEMHTML_FILE_TYPE;
    }

    public static Language getFileLanguage() {
        return BEMHTML_LANGUAGE;
    }

}
