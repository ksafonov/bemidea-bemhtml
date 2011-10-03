package bem.idea.bemhtml.structure;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.TextEditorBasedStructureViewModel;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class BemHtmlStructureViewModel extends TextEditorBasedStructureViewModel
                implements StructureViewModel.ElementInfoProvider{

    private PsiFile myFile;

    public BemHtmlStructureViewModel(final PsiFile file) {
        super(file);
        myFile = file;
    }

    @NotNull
    public StructureViewTreeElement getRoot() {
        return new BemHtmlStructureViewElement(myFile, null, null, true);
    }

    @NotNull
    public Sorter[] getSorters() {
        return new Sorter[]{Sorter.ALPHA_SORTER};
    }

    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        return false;
    }
}
