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
    private IElementType myType;
    private String presentableText = null;
    private String locationText = null;
    private boolean wantChildren = false;

    public BemHtmlStructureViewElement(PsiElement element,
                                       String presentableText,
                                       String locationText,
                                       boolean wantChildren) {
        myElement = element;
        myType = myElement.getNode().getElementType();
        this.presentableText = presentableText;
        this.locationText = locationText;
        this.wantChildren = wantChildren;
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
        if (myType == KEYWORD_BLOCK && wantChildren) return findBlockChildren(myElement);
        if (myType == KEYWORD_ELEM && wantChildren) return findElemChildren(myElement);

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

        return convertChildren(null, childrenElements);
    }


    private StructureViewTreeElement[] findBlockChildren(PsiElement block) {
        final List<BemHtmlElement> childrenElements = new ArrayList<BemHtmlElement>();

        PsiElement next = block;
        IElementType type;
        do {
            next = next.getNextSibling();
            if (next instanceof BemHtmlElement) {
                type = next.getNode().getElementType();
                if (type == KEYWORD_ELEM || type == KEYWORD_MOD) {
                    childrenElements.add((BemHtmlElement)next);
                } else if (type == KEYWORD_BLOCK) break;
            }
        } while (next.getNextSibling() != null);

        return convertChildren(new BemHtmlStructureViewElement(block, "declaration", null, false),
                               childrenElements);
    }

    private StructureViewTreeElement[] findElemChildren(PsiElement elem) {
        final List<BemHtmlElement> childrenElements = new ArrayList<BemHtmlElement>();

        PsiElement next = elem;
        IElementType type;
        do {
            next = next.getNextSibling();
            if (next instanceof BemHtmlElement) {
                type = next.getNode().getElementType();
                if (type == KEYWORD_ELEMMOD) {
                    childrenElements.add((BemHtmlElement)next);
                } else if (type == KEYWORD_BLOCK || type == KEYWORD_ELEM) break;
            }
        } while (next.getNextSibling() != null);

        return convertChildren(new BemHtmlStructureViewElement(elem, "declaration", null, false),
                               childrenElements);
    }

    private String findPresentableText(BemHtmlElement element) {
        IElementType type = element.getNode().getElementType();
        if (type == KEYWORD_BLOCK) {
            return "block: " + findOnePresentableText(element);
        } else if (type == KEYWORD_ELEM) {
            return "elem: " + findOnePresentableText(element);
        } else if (type == KEYWORD_MOD) {
            return "mod: " + findTwoPresentableText(element);
        } else if (type == KEYWORD_ELEMMOD) {
            return "elemMod: " + findTwoPresentableText(element);
        }
        return "<none>";
    }

    private String findOnePresentableText(BemHtmlElement element) {
        PsiElement next = element.getNextSibling();
        if (next instanceof PsiWhiteSpace) {
            next = next.getNextSibling();
            if (next instanceof BemHtmlElement) {
                if (next.getNode().getElementType() == BEM_VALUE) {
                    return next.getText();
                }
            }
        }
        return "<none>";
    }

    private String findTwoPresentableText(BemHtmlElement element) {
        PsiElement next = element.getNextSibling();
        if (next instanceof PsiWhiteSpace) {
            next = next.getNextSibling();
            if (next instanceof BemHtmlElement && next.getNode().getElementType() == BEM_VALUE) {
                String pText = next.getText();
                next = next.getNextSibling();
                if (next instanceof PsiWhiteSpace) {
                    next = next.getNextSibling();
                    if (next instanceof BemHtmlElement && next.getNode().getElementType() == BEM_VALUE) {
                        return pText + " " + next.getText();
                    }
                }
            }
        }
        return "<none>";
    }

    private StructureViewTreeElement[] convertChildren(StructureViewTreeElement root,
                                                       List<BemHtmlElement> childrenElements) {
        if (childrenElements.size() == 0) return new StructureViewTreeElement[0];
        int shift = (root == null ? 0 : 1);
        StructureViewTreeElement[] children = new StructureViewTreeElement[childrenElements.size() + shift];
        if (root != null) children[0] = root;
        for (int i = 0; i < childrenElements.size(); i++) {
            BemHtmlElement element = childrenElements.get(i);
            children[i + shift] = new BemHtmlStructureViewElement(element, findPresentableText(element), null, true);
        }

        return children;
    }

}
