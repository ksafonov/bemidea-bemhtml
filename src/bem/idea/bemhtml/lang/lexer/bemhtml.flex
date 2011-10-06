package bem.idea.bemhtml.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

%%

%unicode

%public
%class BemHtmlLexer
%implements FlexLexer
%type IElementType
%{
    private BemHtmlCustomLexer myLexer = new BemHtmlCustomLexer();

    private IElementType nextToken() {
         myLexer.next();
         yypushback(myLexer.getPushback(yylength()));
         return myLexer.getType();
    }

%}

%function advance

LineTerminator = \r|\n|\r\n

All = (. | {LineTerminator})+

%state NEXT

%%

<YYINITIAL> {
    {All}       { myLexer.parse(yytext().toString()); yypushback(yytext().length()); yybegin(NEXT); }
}

<NEXT> {
    {All}       { yybegin(NEXT); return nextToken(); }
}
