package bem.idea.bemhtml.lang.lexer.custom;

public class BHToken {
    private BHTokenType type;
    public int tokenStart;
    public int tokenEnd;

    BHToken(BHTokenType type, int tokenStart, int tokenEnd) {
        this.type = type;
        this.tokenStart = tokenStart;
        this.tokenEnd = tokenEnd;
    }

    public BHTokenType getType() {
        return type;
    }

    public void setType(BHTokenType type) {
        this.type = type;
    }

    public int getTokenStart() {
        return tokenStart;
    }

    public int getTokenEnd() {
        return tokenEnd;
    }

    public void increment() {
        tokenEnd++;
    }

    public void invalidate(BHTokenType errorType) {
        setType(errorType);
    }

    public String toString() {
        return type + "[" + tokenStart + ":" + tokenEnd + "]";
    }
}
