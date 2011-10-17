package bem.idea.bemhtml.lang.lexer.custom;

import java.util.List;

public class BHList {
    private List<BHToken> filtered;
    private List<BHToken> all;

    BHList(List<BHToken> filtered, List<BHToken> all) {
        this.filtered = filtered;
        this.all = all;
    }

    public List<BHToken> getFiltered() {
        return filtered;
    }

    public List<BHToken> getAll() {
        return all;
    }
}
