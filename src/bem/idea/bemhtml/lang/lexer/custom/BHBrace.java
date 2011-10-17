package bem.idea.bemhtml.lang.lexer.custom;

public class BHBrace {
    private int l;
    private int r = -1;

    BHBrace(int l) {
        this.l = l;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getL() {
        return l;
    }

    public int getR() {
        return r;
    }

    public String toString() {
        return l + "/" + r;
    }
}
