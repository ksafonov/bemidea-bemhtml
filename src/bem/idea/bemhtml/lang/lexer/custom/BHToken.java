package bem.idea.bemhtml.lang.lexer.custom;

public class BHToken {
    private BHTokenType type;
    private int start;
    private int end;

    BHToken(BHTokenType type, int start, int end) {
        this.type = type;
        this.start = start;
        this.end = end;
    }

    public BHTokenType getType() {
        return type;
    }

    public void setType(BHTokenType type) {
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void increment() {
        end++;
    }

    public void invalidate(BHTokenType errorType) {
        setType(errorType);
    }

    public String toString() {
        return type + "[" + start + ":" + end + "]";
    }
}
