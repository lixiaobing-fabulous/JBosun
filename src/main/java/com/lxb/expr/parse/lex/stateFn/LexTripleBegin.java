package com.lxb.expr.parse.lex.stateFn;

import com.lxb.expr.parse.lex.LexUtils;
import com.lxb.expr.parse.lex.Lexer;

public class LexTripleBegin implements StateFn{
    @Override
    public StateFn apply(Lexer lexer) {
        while (true) {
            char ch = lexer.next();
            if (ch == '\'') {
                if (lexer.next() == '\'') {
                    return new LexStringTripleEnd();
                } else {
                    lexer.backup();
                }
                return lexer.errorf("invalid start of string, must use double qutoes or triple single quotes");
            }
            if (ch == LexUtils.EOF) {
                return lexer.errorf("unterminated string");
            }
        }

    }
}
