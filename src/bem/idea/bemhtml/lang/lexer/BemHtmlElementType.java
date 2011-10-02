package bem.idea.bemhtml.lang.lexer;

import bem.idea.bemhtml.file.BemHtmlFileType;
import com.intellij.psi.tree.IElementType;

public class BemHtmlElementType extends IElementType {

    private String name;

    public BemHtmlElementType(String name) {
        super(name, BemHtmlFileType.BEMHTML_FILE_TYPE.getLanguage());

        this.name = name;
    }

    public String toString() {
        return name;
    }

}
