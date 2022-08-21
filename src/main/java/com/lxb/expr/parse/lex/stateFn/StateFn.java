package com.lxb.expr.parse.lex.stateFn;

import com.lxb.expr.parse.lex.Lexer;

@FunctionalInterface
public interface StateFn {
    StateFn apply(Lexer lexer);
}
