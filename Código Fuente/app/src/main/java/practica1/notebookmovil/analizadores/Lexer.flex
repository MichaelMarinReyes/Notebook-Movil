package practica1.notebookmovil.analizadores;

import java.util.ArrayList;
import java_cup.runtime.*;
import practica1.notebookmovil.analizadores.errores.ErrorLexico;

%%
%public
%class Lexer
%unicode
%line
%column
%cup
%ignorecase
%{
    public static ArrayList<ErrorLexico> errores = new ArrayList<>();
    public static ArrayList<Token> tokens = new ArrayList<>();
    public ArrayList<ErrorLexico> getErrores() {
        return errores;
    }
%}

%%

[ \t\n\r\f]       { /* Ignorar espacios en blanco */ }

"format"          { return new Symbol(ParserSym.FORMAT, yyline+1, yycolumn+1, yytext()); }
"plot"            { return new Symbol(ParserSym.PLOT, yyline+1, yycolumn+1, yytext()); }
"print"           { return new Symbol(ParserSym.PRINT, yyline+1, yycolumn+1, yytext()); }
"reporte"         { return new Symbol(ParserSym.REPORTE, yyline+1, yycolumn+1,yytext()); }
"operadores"      { return new Symbol(ParserSym.OPERADORES, yyline+1, yycolumn+1, yytext()); }
"errores"         { return new Symbol(ParserSym.ERRORES, yyline+1, yycolumn+1, yytext()); }
[0-9]+\.[0-9]+    { return new Symbol(ParserSym.NUMERO, yyline+1, yycolumn+1, Double.parseDouble(yytext())); }
[0-9]+            { return new Symbol(ParserSym.NUMERO, yyline+1, yycolumn+1, Double.parseDouble(yytext())); }
"+"               { return new Symbol(ParserSym.SUMA, yyline+1, yycolumn+1, yytext()); }
"-"               { return new Symbol(ParserSym.RESTA, yyline+1, yycolumn+1, yytext()); }
"*"               { return new Symbol(ParserSym.MULTIPLICACION, yyline+1, yycolumn+1, yytext()); }
"/"               { return new Symbol(ParserSym.DIVISION, yyline+1, yycolumn+1, yytext()); }
"("               { return new Symbol(ParserSym.PARENTESIS_ABRE, yyline+1, yycolumn+1, yytext()); }
")"               { return new Symbol(ParserSym.PARENTESIS_CIERRA, yyline+1, yycolumn+1, yytext()); }
"^"               { return new Symbol(ParserSym.POTENCIA, yyline+1, yycolumn+1, yytext()); }
"="               { return new Symbol(ParserSym.IGUAL, yyline+1, yycolumn+1, yytext()); }
"#"               { return new Symbol(ParserSym.HEADER1, yyline+1, yycolumn+1, yytext()); }
"##"              { return new Symbol(ParserSym.HEADER2, yyline+1, yycolumn+1, yytext()); }
"###"             { return new Symbol(ParserSym.HEADER3, yyline+1, yycolumn+1, yytext()); }
"####"            { return new Symbol(ParserSym.HEADER4, yyline+1, yycolumn+1, yytext()); }
"#####"           { return new Symbol(ParserSym.HEADER5, yyline+1, yycolumn+1, yytext()); }
"######"          { return new Symbol(ParserSym.HEADER6, yyline+1, yycolumn+1, yytext()); }
"**"              { return new Symbol(ParserSym.TEXT_NEGRITA, yyline+1, yycolumn+1, yytext()); }
"***"             { return new Symbol(ParserSym.TEXT_NEGRITA_ITALICA, yyline+1, yycolumn+1, yytext()); }
\"                { return new Symbol(ParserSym.COMILLAS, yyline+1, yycolumn+1, yytext()); }
","               { return new Symbol(ParserSym.COMA, yyline+1, yycolumn+1, yytext()); }
"."               { return new Symbol(ParserSym.PUNTO, yyline+1, yycolumn+1, yytext()); }
[0-9a-zA-Z]+      { return new Symbol(ParserSym.CADENA, yyline+1, yycolumn+1, yytext()); }
[a-zA-Z_][a-zA-Z0-9_]* { return new Symbol(ParserSym.VARIABLE, yyline+1, yycolumn+1, yytext()); }
<<EOF>>           { return new Symbol(ParserSym.EOF); }
[^]               { errores.add(new ErrorLexico(yytext(), yyline + 1, yycolumn + 1, "Léxico", "Caracter desconocido: " + yytext()));
          System.err.println("Error léxico: " + yytext() + " linea: " + String.valueOf(yyline + 1) + " columna: " + String.valueOf(yycolumn + 1));}