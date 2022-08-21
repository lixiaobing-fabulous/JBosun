package com.lxb.expr.parse.lex.stateFn;

import com.lxb.expr.parse.lex.LexUtils;
import com.lxb.expr.parse.lex.ItemType;
import com.lxb.expr.parse.lex.Lexer;

public class LexPrefixEnd implements StateFn {
    @Override
    public StateFn apply(Lexer lexer) {
        while (true) {
            char ch = lexer.next();
            if (ch == '"') {
                if (lexer.next() == ']') {
                    lexer.emit(ItemType.itemPrefix);
                    return new LexItem();
                }
            }
            if (ch == LexUtils.EOF) {
                return lexer.errorf("unterminated prefix string, must use double quotes e.g [\"foo\"]");
            }
        }
    }
}
