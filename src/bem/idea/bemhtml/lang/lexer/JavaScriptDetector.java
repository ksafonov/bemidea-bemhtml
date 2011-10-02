package bem.idea.bemhtml.lang.lexer;

import java.util.*;

public class JavaScriptDetector {

    private static final Set<String> jsKeywords;
    private static final Set<String> bemKeywords;
    static {
        jsKeywords = new HashSet<String>();
        jsKeywords.add("delete");
        jsKeywords.add("do");
        jsKeywords.add("for");
        jsKeywords.add("function");
        jsKeywords.add("if");
        jsKeywords.add("new");
        jsKeywords.add("return");
        jsKeywords.add("switch");
        jsKeywords.add("throw");
        jsKeywords.add("try");
        jsKeywords.add("typeof");
        jsKeywords.add("var");
        jsKeywords.add("while");

        bemKeywords = new HashSet<String>();
        bemKeywords.add("block");
        bemKeywords.add("elemMod");
        bemKeywords.add("elem");
        bemKeywords.add("mod");
        bemKeywords.add("default");
        bemKeywords.add("tag");
        bemKeywords.add("attrs");
        bemKeywords.add("cls");
        bemKeywords.add("bem");
        bemKeywords.add("js");
        bemKeywords.add("jsAttr");
        bemKeywords.add("mix");
        bemKeywords.add("content");
    }

    enum Token {
        STRING,
        WHITESPACE,
        NEWLINE,
        PUNCTUATION,
        DELIM,
        COLON,
        SEMICOLON,
        IDENT,
        JS_KEYWORD,
        BEM_KEYWORD,
        JS_EXPR,
        B_BLOCK, // {}
        R_BLOCK, // ()
        S_BLOCK // []
    }

    private static String getBlock(String src) {
        if (src == null || src.charAt(0) != '{') return null;
        StringBuilder sb = new StringBuilder();
        char c;
        int braceCounter = 0;
        int se;
        for (int i = 0, l = src.length(); i < l; i++) {
            c = src.charAt(i);
            if (c == '"' || c == '\'') {
                se = findStringEnd(src, c, i + 1);
                if (se == -1) return null;
                sb.append(src.substring(i, se + 1));
                i = se;
            } else {
                if (c == '{') braceCounter++;
                else if (c == '}') braceCounter--;
                sb.append(c);
            }
            if (braceCounter == 0) return sb.toString();
        }
        return null;
    }

    private static int findStringEnd(String s, char q, int start) {
        char c;
        for (int i = start, l = s.length(); i < l; i++) {
            c = s.charAt(i);
            if (c == q) return i;
            else if (c == '\\') i++;
        }
        return -1;
    }

    private static int findBlockEnd(String s, char sc, char ec, int start) {
        char c;
        int counter = 1;
        int se;
        for (int i = start, l = s.length(); i < l; i++) {
            c = s.charAt(i);
            if (c == '"' || c == '\'') {
                se = findStringEnd(s, c, i + 1);
                i = se;
            } else {
                if (c == sc) counter++;
                else if (c == ec) counter--;
            }
            if (counter == 0) return i;
        }
        return -1;
    }

    private static List<Token> getTokens(String content) {
        List<Token> tokens = new ArrayList<Token>();
        StringBuilder sb = new StringBuilder();
        Token toAdd;
        char c;
        int te;
        for (int i = 0, l = content.length(); i < l; i++) {
            toAdd = null;
            c = content.charAt(i);
            if (c == '"' || c == '\'') {
                te = findStringEnd(content, c, i + 1);
                toAdd = Token.STRING;
                i = te;
            } else {
                switch(c) {
                    case '{':
                        te = findBlockEnd(content, '{', '}', i + 1);
                        toAdd = Token.B_BLOCK;
                        i = te;
                        break;
                    case '[':
                        te = findBlockEnd(content, '[', ']', i + 1);
                        if (te != -1) {
                            toAdd = Token.S_BLOCK;
                            i = te;
                        } else toAdd = Token.PUNCTUATION;
                        break;
                    case '(':
                        te = findBlockEnd(content, '(', ')', i + 1);
                        if (te != -1) {
                            toAdd = Token.R_BLOCK;
                            i = te;
                        } else toAdd = Token.PUNCTUATION;
                        break;
                    case ' ': case '\t':
                        toAdd = Token.WHITESPACE;
                        break;
                    case '\n': case '\r':
                        toAdd = Token.NEWLINE;
                        break;
                    case ':':
                        toAdd = Token.COLON;
                        break;
                    case ';':
                        toAdd = Token.SEMICOLON;
                        break;
                    case ',':
                        toAdd = Token.DELIM;
                        break;
                    case '+': case '-': case '*': case '/': case '%': case '^':
                    case '.': case '?': case '!': case '\\': case ']': case ')':
                    case '}': case '&': case '|':
                        toAdd = Token.PUNCTUATION;
                        break;
                    default:
                        sb.append(c);
                }
            }
            if (toAdd != null) {
                if (sb.length() > 0) {
                    tokens.add(getToken(sb.toString()));
                    sb = new StringBuilder();
                }
                tokens.add(toAdd);
            }
        }

        if (sb.length() > 0) tokens.add(getToken(sb.toString()));

        return tokens;
    }

    private static Token getToken(String s) {
        if (jsKeywords.contains(s)) return Token.JS_KEYWORD;
        else if (bemKeywords.contains(s)) return Token.BEM_KEYWORD;
        else return Token.IDENT;
    }

    private static String getContent(String src) {
        return src.substring(1, src.length() - 1).trim();
    }

    private static List<Token> joinExpr(List<Token> tokens) {
        List<Token> joined = new ArrayList<Token>();
        boolean join = false;
        Token last = null;
        for (Token token : tokens) {
            switch(token) {
                case IDENT:
                case PUNCTUATION:
                case R_BLOCK:
                case S_BLOCK:
                case STRING:
                    if (!join) {
                        joined.add(Token.JS_EXPR);
                        join = true;
                    }
                    break;
                case JS_KEYWORD:
                case BEM_KEYWORD:
                case B_BLOCK:
                    if (!join) joined.add(token);
                    break;
                case WHITESPACE:
                case NEWLINE:
                    if (last != Token.DELIM &&
                        last != Token.COLON &&
                        last != Token.B_BLOCK &&
                        last != Token.S_BLOCK &&
                        last != Token.R_BLOCK &&
                        last != token) joined.add(token);
                    join = false;
                    break;
                case DELIM:
                case COLON:
                case SEMICOLON:
                    joined.add(token);
                    join = false;
                    break;
            }
            last = token;
        }
        return joined;
    }

    private static boolean _isJavaScript(List<Token> tokens) {
        if (tokens.size() == 0) return false;
        if (tokens.contains(Token.SEMICOLON)) return true;

        Token[] _t = tokens.toArray(new Token[0]);

        if (_t[0] == Token.JS_KEYWORD) return true;
        if (_t[0] == Token.BEM_KEYWORD) return false;
        if (_t.length > 1 && _t[0] == Token.JS_EXPR &&
            (_t[1] == Token.COLON ||
             _t[1] == Token.DELIM ||
             _t[1] == Token.B_BLOCK)) return false;

        return true;
    }

    public static boolean isJavaScript(String src) {
        String block = JavaScriptDetector.getBlock(src);
        if (block != null) {
            String content = JavaScriptDetector.getContent(block);
            if (content.length() > 0) {
                List<Token> tokens = JavaScriptDetector.getTokens(content);
                List<Token> joined = JavaScriptDetector.joinExpr(tokens);
                return JavaScriptDetector._isJavaScript(joined);
            }
        }
        return false;
    }

}
