package com.lxb.expr.parse.lex.stateFn;

import com.lxb.expr.parse.lex.ItemType;
import com.lxb.expr.parse.lex.LexUtils;
import com.lxb.expr.parse.lex.Lexer;

public class LexStringTripleEnd implements StateFn {
    @Override
    public StateFn apply(Lexer lexer) {
        int count = 0;
        while (true) {
            char ch = lexer.next();
            if (ch == '\'') {
                count++;
                if (count == 3) {
                    lexer.emit(ItemType.itemTripleQuotedString);
                    return new LexItem();
                }
            } else if (ch == LexUtils.EOF) {
                return lexer.errorf("unterminated string");
            } else {
                count = 0;
            }
        }
    }
}
