package bem.idea.bemhtml.lang.lexer.custom;

import com.intellij.psi.tree.IElementType;

public interface Tokenizer {

    int getTokenStart(int index);

    int getTokenEnd(int index);

    IElementType getTokenType(int index);

    void resetSequence(CharSequence buffer);

}
