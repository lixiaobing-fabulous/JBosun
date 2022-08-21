package com.lxb.expr.parse.lex.stateFn;

import com.lxb.expr.parse.lex.LexUtils;
import com.lxb.expr.parse.lex.ItemType;
import com.lxb.expr.parse.lex.Lexer;

public class LexString implements StateFn {
    @Override
    public StateFn apply(Lexer lexer) {
        while (true) {
            char ch = lexer.next();
            if (ch == '"') {
                lexer.emit(ItemType.itemString);
                return new LexItem();
            }
            if (ch == LexUtils.EOF) {
                return lexer.errorf("unterminated string");
            }
        }
    }
}
