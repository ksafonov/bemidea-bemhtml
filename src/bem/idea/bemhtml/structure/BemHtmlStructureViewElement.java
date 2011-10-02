package bem.idea.bemhtml.structure;

import bem.idea.bemhtml.lang.psi.BemHtmlElement;
import bem.idea.bemhtml.lang.psi.BemHtmlPsiFile;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.tree.IElementType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static bem.idea.bemhtml.lang.lexer.BemHtmlTokenTypes.*;

public class BemHtmlStructureViewElement implements StructureViewTreeElement {

    private PsiElement myElement;
    private String presentableText = null;
    private String locationText = null;

    public BemHtmlStructureViewElement(PsiElement element,
                                       String presentableText,
                                       String locationText) {
        myElement = element;
        this.presentableText = presentableText;
        this.locationText = locationText;
    }

    public Object getValue() {
        return myElement;
    }

    public void navigate(boolean requestFocus) {
        ((NavigationItem) myElement).navigate(requestFocus);
    }

    public boolean canNavigate() {
        return ((NavigationItem) myElement).canNavigate();
    }

    public boolean canNavigateToSource() {
        return ((NavigationItem) myElement).canNavigateToSource();
    }

    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            public String getPresentableText() {
                return presentableText;
            }

            public TextAttributesKey getTextAttributesKey() {
                return null;
            }

            public String getLocationString() {
                return locationText;
            }

            public Icon getIcon(boolean open) {
                return null;
            }
        };
    }

    public TreeElement[] getChildren() {
        if (!(myElement instanceof BemHtmlElement)) return new TreeElement[0];

        if (myElement instanceof BemHtmlPsiFile && myElement.getFirstChild() !=null) return findBlocks(myElement.getFirstChild());
        if (myElement.getNode().getElementType() == KEYWORD_BLOCK) return findBlockChildren(myElement);

        return new TreeElement[0];
    }

    private StructureViewTreeElement[] findBlocks(PsiElement firstChild) {
        final List<BemHtmlElement> childrenElements = new ArrayList<BemHtmlElement>();

        PsiElement next = firstChild;
        do {
            if (next.getNode().getElementType() == KEYWORD_BLOCK) {
                childrenElements.add((BemHtmlElement)next);
            }
            next = next.getNextSibling();
        } while (next != null);

        return convertChildren(childrenElements);
    }


    private StructureViewTreeElement[] findBlockChildren(PsiElement block) {
        final List<BemHtmlElement> childrenElements = new ArrayList<BemHtmlElement>();

        PsiElement next = block;
        IElementType type;
        do {
            next = next.getNextSibling();
            if (next instanceof BemHtmlElement) {
                type = next.getNode().getElementType();
                if (type == KEYWORD_ELEM) {
                    childrenElements.add((BemHtmlElement)next);
                } else if (type == KEYWORD_BLOCK) break;
            }
        } while (next.getNextSibling() != null);

        return convertChildren(childrenElements);
    }

    private String findPresentableText(BemHtmlElement element) {
        IElementType type = element.getNode().getElementType();
        String pText = "<none>";
        if (type == KEYWORD_BLOCK) {
            pText = findOnePresentableText(element);
            return "block: " + pText;
        } else if (type == KEYWORD_ELEM) {
            pText = findOnePresentableText(element);
            return "elem: " + pText;
        }
        return pText;
    }

    private String findOnePresentableText(BemHtmlElement element) {
        PsiElement next = element.getNextSibling();
        if (next instanceof PsiWhiteSpace) {
            next = next.getNextSibling();
            if (next instanceof BemHtmlElement) {
                BemHtmlElement _next = (BemHtmlElement)next;
                if (_next.getNode().getElementType() == BEM_VALUE) {
                    return _next.getText();
                }
            }
        }
        return "<none>";
    }

    private StructureViewTreeElement[] convertChildren(List<BemHtmlElement> childrenElements) {
        StructureViewTreeElement[] children = new StructureViewTreeElement[childrenElements.size()];
        for (int i = 0; i < children.length; i++) {
            BemHtmlElement element = childrenElements.get(i);
            children[i] = new BemHtmlStructureViewElement(element,
                    findPresentableText(element), null);
        }

        return children;
    }

}
