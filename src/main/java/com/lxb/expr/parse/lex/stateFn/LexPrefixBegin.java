package com.lxb.expr.parse.lex.stateFn;

import com.lxb.expr.parse.lex.LexUtils;
import com.lxb.expr.parse.lex.Lexer;

public class LexPrefixBegin implements StateFn {
    @Override
    public StateFn apply(Lexer lexer) {
        while (true) {
            char ch = lexer.next();
            if (ch == '"') {
                return new LexPrefixEnd();
            }
            if (ch == LexUtils.EOF) {
                return lexer.errorf("unterminated prefix string, must use double quotes e.g [\"foo\"]");
            }
        }

    }
}
