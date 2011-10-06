package bem.idea.bemhtml.lang.parser;

import bem.idea.bemhtml.file.BemHtmlFileType;
import bem.idea.bemhtml.lang.lexer.BemHtmlParsingLexer;
import bem.idea.bemhtml.lang.lexer.BemHtmlTokenSets;
import bem.idea.bemhtml.lang.lexer.BemHtmlTokenTypes;
import bem.idea.bemhtml.lang.psi.impl.*;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

public class BemHtmlParserDefinition implements ParserDefinition {

    private static final IFileElementType FILE_ELEMENT_TYPE = new IFileElementType(BemHtmlFileType.BEMHTML_LANGUAGE);

    @NotNull
    public Lexer createLexer(Project project) {
        return new BemHtmlParsingLexer();
    }

    public PsiParser createParser(Project project) {
        return new BemHtmlParser();
    }

    public IFileElementType getFileNodeType() {
        return FILE_ELEMENT_TYPE;
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return BemHtmlTokenSets.WHITESPACE_TOKEN_SET;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return BemHtmlTokenSets.COMMENTS_TOKEN_SET;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return BemHtmlTokenSets.STRING_TOKEN_SET;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        if (node.getElementType() == BemHtmlTokenTypes.JAVASCRIPT_CODE) {
            return new BemHtmlJavaScriptCodeImpl(node);
        } else if (node.getElementType() == BemHtmlTokenTypes.SINGLE_JAVASCRIPT_CODE) {
                return new BemHtmlJavaScriptCodeImpl(node, true, true);
        } else if (node.getElementType() == BemHtmlTokenTypes.FIRST_JAVASCRIPT_CODE) {
                return new BemHtmlJavaScriptCodeImpl(node, true, false);
        } else if (node.getElementType() == BemHtmlTokenTypes.LAST_JAVASCRIPT_CODE) {
                return new BemHtmlJavaScriptCodeImpl(node, false, true);
        } else if (node.getElementType() == BemHtmlTokenTypes.JS_EXPRESSION) {
            return new BemHtmlAsgnExprImpl(node);
        } else if (node.getElementType() == BemHtmlTokenTypes.SINGLE_JS_EXPRESSION) {
            return new BemHtmlAsgnExprImpl(node, true, true);
        } else if (node.getElementType() == BemHtmlTokenTypes.FIRST_JS_EXPRESSION) {
            return new BemHtmlAsgnExprImpl(node, true, false);
        } else if (node.getElementType() == BemHtmlTokenTypes.LAST_JS_EXPRESSION) {
            return new BemHtmlAsgnExprImpl(node, false, true);
        } else if (node.getElementType() == BemHtmlTokenTypes.BEM_VALUE) {
            return new BemHtmlBemValueImpl(node);
        }
        return new BemHtmlElementImpl(node);
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new BemHtmlPsiFileImpl(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }
}
