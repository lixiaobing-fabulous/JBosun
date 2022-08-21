package com.lxb.expr.parse.lex;

public class LexUtils {
    public static String SYMBOLS = "!<>=&|+-*/%";
    public static char EOF = (char) -1;

    public static boolean isSpace(char ch) {
        return Character.isSpaceChar(ch) || '\t' == ch || '\n' == ch;
    }

    public static boolean isVarchar(char ch) {
        return ch == '_' || Character.isAlphabetic(ch);
    }

    public static boolean isSymbol(char ch) {
        return SYMBOLS.indexOf(ch) != -1;
    }

    public static boolean isNumber(char ch) {
        return Character.isDigit(ch) || ch == '.';
    }
}
