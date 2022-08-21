package com.lxb.expr.parse.lex;


class LexerTest {
    public static void main(String[] args) {
        System.out.println(new Lexer("''' unclosed triple quote ''").items);
    }

}