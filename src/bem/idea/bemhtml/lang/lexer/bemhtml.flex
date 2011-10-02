package bem.idea.bemhtml.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import java.util.Map;
import java.util.HashMap;

%%

%unicode

%public
%class BemHtmlLexer
%implements FlexLexer
%type IElementType
%{
    private int rBraceCounter = 0;
    private int sBraceCounter = 0;
    private int bBraceCounter = 0;
    private int valCounter = 0;
    private boolean isEmpty = true;
    private int postState = YYINITIAL;
    private String context = null;
    private IElementType t = null;
    private IElementType ojseToken = null;

    private static Map<String, int[]> contextValNum;
    private static Map<String, IElementType> tokens;
    static {
        int[] x;
        contextValNum = new HashMap<String, int[]>();
        x = new int[2]; x[0] = 1; x[1] = 1;
        contextValNum.put("block", x);
        contextValNum.put("elem", x);
        contextValNum.put("_js", x);
        x = new int[2]; x[0] = 2; x[1] = 2;
        contextValNum.put("mod", x);
        contextValNum.put("elemMod", x);

        tokens = new HashMap<String, IElementType>();
        tokens.put("block", BemHtmlTokenTypes.KEYWORD_BLOCK);
        tokens.put("elem", BemHtmlTokenTypes.KEYWORD_ELEM);
        tokens.put("mod", BemHtmlTokenTypes.KEYWORD_MOD);
        tokens.put("elemMod", BemHtmlTokenTypes.KEYWORD_ELEMMOD);
        tokens.put("default", BemHtmlTokenTypes.KEYWORD_DEFAULT);
        tokens.put("tag", BemHtmlTokenTypes.KEYWORD_TAG);
        tokens.put("attrs", BemHtmlTokenTypes.KEYWORD_ATTRS);
        tokens.put("cls", BemHtmlTokenTypes.KEYWORD_CLS);
        tokens.put("bem", BemHtmlTokenTypes.KEYWORD_BEM);
        tokens.put("js", BemHtmlTokenTypes.KEYWORD_JS);
        tokens.put("jsAttr", BemHtmlTokenTypes.KEYWORD_JSATTR);
        tokens.put("mix", BemHtmlTokenTypes.KEYWORD_MIX);
        tokens.put("content", BemHtmlTokenTypes.KEYWORD_CONTENT);
        tokens.put("{", BemHtmlTokenTypes.LEFT_BRACE);
        tokens.put("}", BemHtmlTokenTypes.RIGHT_BRACE);
        tokens.put(",", BemHtmlTokenTypes.KEYWORDS_DELIM);
        tokens.put(":", BemHtmlTokenTypes.KEYWORDS_COLON);
    }

    private void initOJSE(String context, IElementType ojseToken) {
        this.ojseToken = ojseToken;
        this.context = context;
        valCounter = 0;
        rBraceCounter = 0;
        sBraceCounter = 0;
        isEmpty = true;
    }

    private void initOJSE(IElementType ojseToken) {
        initOJSE(yytext().toString(), ojseToken);
    }

    private IElementType continueOJSE() {
        isEmpty = false;
        yybegin(OPEN_JS_EXPR);
        return ojseToken;
    }

    private IElementType finalizeOJSE(IElementType stopToken) {
        int[] nn = contextValNum.get(context);
        int min = nn[0];
        int max = nn[1];

        if (!isEmpty) valCounter++;

        if ((isEmpty && min == 0) || (valCounter >= min && valCounter <= max)) {
            yypushback(yytext().length());
            yybegin(postState);
            return null;
        } else if (!isEmpty && valCounter < min && BemHtmlTokenTypes.WHITE_SPACE == stopToken) {
            yybegin(OPEN_JS_EXPR);
            return stopToken;
        } else {
            return invalid();
        }
    }

    private IElementType invalid() {
        yybegin(yystate());
        return BemHtmlTokenTypes.BAD_CHARACTER;
    }

    private boolean isJavaScript() {
        return JavaScriptDetector.isJavaScript(yytext().toString());
    }

%}

%function advance

LineTerminator = \r|\n|\r\n
WhiteSpace = [ \t]
AnySpace = {LineTerminator} | {WhiteSpace} | [\f]

StringLiteral1 = \" ( \\\" | [^\"\n\r] )* \"
StringLiteral2 = \' ( \\\' | [^\'\n\r] )* \'
StringLiteral = {StringLiteral1} | {StringLiteral2}

BemMatchKeyword = "block" | "elemMod" | "elem" | "mod"
BhKeyword = "default" | "tag" | "attrs" | "cls" | "bem" | "js" | "jsAttr" | "mix" | "content"

Block = "{" (. | {LineTerminator})+

%state PRE_OPEN_JS_EXPR, OPEN_JS_EXPR
%state POST_BH_KEYWORD, BH_KEYWORD_VALUE, POST_BH_KEYWORD_VALUE
%state POST_BM_KEYWORD
%state JAVASCRIPT_CODE

%%

<YYINITIAL> {
    {AnySpace}+       { yybegin(YYINITIAL); return BemHtmlTokenTypes.WHITE_SPACE; }
    {BemMatchKeyword} {
                        postState = POST_BM_KEYWORD;
                        initOJSE(BemHtmlTokenTypes.BEM_VALUE);
                        yybegin(PRE_OPEN_JS_EXPR);
                        return tokens.get(yytext());
                      }
    {BhKeyword}       { yybegin(POST_BH_KEYWORD); return tokens.get(yytext()); }
    {Block}           {
                        if (isJavaScript()) {
                            bBraceCounter = 1;
                            yypushback(yytext().length() - 1);
                            yybegin(JAVASCRIPT_CODE);
                            return BemHtmlTokenTypes.LEFT_BRACE;
                        } else {
                            yypushback(yytext().length() - 1);
                            yybegin(YYINITIAL);
                            return BemHtmlTokenTypes.LEFT_BRACE;
                        }
                      }
    "}"               { yybegin(YYINITIAL); return BemHtmlTokenTypes.RIGHT_BRACE; }
    ","               { yybegin(YYINITIAL); return BemHtmlTokenTypes.KEYWORDS_DELIM; }
    .                 {
                        postState = POST_BH_KEYWORD;
                        yypushback(1);
                        initOJSE("_js", BemHtmlTokenTypes.KEYWORDS_VALUE);
                        yybegin(OPEN_JS_EXPR);
                      }
}

<POST_BH_KEYWORD> {
    {WhiteSpace}+     { yybegin(POST_BH_KEYWORD); return BemHtmlTokenTypes.WHITE_SPACE; }
    {LineTerminator}+ { yybegin(YYINITIAL); return BemHtmlTokenTypes.WHITE_SPACE; }
    ","               { yybegin(YYINITIAL); return BemHtmlTokenTypes.KEYWORDS_DELIM; }
    ":"               { yybegin(BH_KEYWORD_VALUE); return BemHtmlTokenTypes.KEYWORDS_COLON; }
    "{"               { yypushback(1); yybegin(YYINITIAL); }
    "}"               { yypushback(1); yybegin(YYINITIAL); }
    .                 { yybegin(POST_BH_KEYWORD); return BemHtmlTokenTypes.BAD_CHARACTER; }
}

<BH_KEYWORD_VALUE> {
    {WhiteSpace}+     { yybegin(BH_KEYWORD_VALUE); return BemHtmlTokenTypes.WHITE_SPACE; }
    {LineTerminator}+ { yybegin(YYINITIAL); return BemHtmlTokenTypes.WHITE_SPACE; }
    "{"               { yypushback(1); yybegin(YYINITIAL); }
    "}"               { yypushback(1); yybegin(YYINITIAL); }
    .                 {
                        postState = POST_BH_KEYWORD_VALUE;
                        yypushback(1);
                        initOJSE("_js", BemHtmlTokenTypes.KEYWORDS_VALUE);
                        yybegin(OPEN_JS_EXPR);
                      }
}

<POST_BH_KEYWORD_VALUE> {
    {WhiteSpace}+     { yybegin(POST_BH_KEYWORD_VALUE); return BemHtmlTokenTypes.WHITE_SPACE; }
    {LineTerminator}+ { yybegin(YYINITIAL); return BemHtmlTokenTypes.WHITE_SPACE; }
    ","               { yybegin(YYINITIAL); return BemHtmlTokenTypes.KEYWORDS_DELIM; }
    "{"               { yypushback(1); yybegin(YYINITIAL); }
    "}"               { yypushback(1); yybegin(YYINITIAL); }
    .                 { yybegin(POST_BH_KEYWORD_VALUE); return BemHtmlTokenTypes.BAD_CHARACTER; }
}

<POST_BM_KEYWORD> {
    {WhiteSpace}+     { yybegin(POST_BM_KEYWORD); return BemHtmlTokenTypes.WHITE_SPACE; }
    {LineTerminator}+ { yybegin(YYINITIAL); return BemHtmlTokenTypes.WHITE_SPACE; }
    ","               { yybegin(YYINITIAL); return BemHtmlTokenTypes.KEYWORDS_DELIM; }
    "{"               { yypushback(1); yybegin(YYINITIAL); }
    "}"               { yypushback(1); yybegin(YYINITIAL); }
    .                 { yybegin(POST_BM_KEYWORD); return BemHtmlTokenTypes.BAD_CHARACTER; }
}

<PRE_OPEN_JS_EXPR> {
    {AnySpace}+     { yybegin(PRE_OPEN_JS_EXPR); return BemHtmlTokenTypes.WHITE_SPACE; }
    .               { yypushback(1); yybegin(OPEN_JS_EXPR); }
}

<OPEN_JS_EXPR> {
    {StringLiteral} { return continueOJSE(); }
    "("             { rBraceCounter++; return continueOJSE(); }
    ")"             { rBraceCounter--; return continueOJSE(); }
    "["             { sBraceCounter++; return continueOJSE(); }
    "]"             { sBraceCounter--; return continueOJSE(); }
    "{"|"}"|":"|"," {
                        if (rBraceCounter > 0 || sBraceCounter > 0) { return continueOJSE(); }
                        else {
                            t = finalizeOJSE(tokens.get(yytext().toString()));
                            if (t != null) return t;
                        }
                    }
    {AnySpace}+     {
                        if (rBraceCounter > 0 || sBraceCounter > 0) { return continueOJSE(); }
                        else {
                            t = finalizeOJSE(BemHtmlTokenTypes.WHITE_SPACE);
                            if (t != null) return t;
                        }
                    }
    .               { return continueOJSE(); }
}

<JAVASCRIPT_CODE> {
    "{"             { bBraceCounter++;
                      yybegin(JAVASCRIPT_CODE); return BemHtmlTokenTypes.JAVASCRIPT_CODE; }
    "}"             { bBraceCounter--;
                      if (bBraceCounter == 0) { yybegin(YYINITIAL); return BemHtmlTokenTypes.RIGHT_BRACE; }
                      else { yybegin(JAVASCRIPT_CODE); return BemHtmlTokenTypes.JAVASCRIPT_CODE; }
                    }
    {StringLiteral} { yybegin(JAVASCRIPT_CODE); return BemHtmlTokenTypes.JAVASCRIPT_CODE; }
    {AnySpace}+     { yybegin(JAVASCRIPT_CODE); return BemHtmlTokenTypes.JAVASCRIPT_CODE; }
    .               { yybegin(JAVASCRIPT_CODE); return BemHtmlTokenTypes.JAVASCRIPT_CODE; }
}