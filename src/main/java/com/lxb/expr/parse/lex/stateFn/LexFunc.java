package com.lxb.expr.parse.lex.stateFn;

import com.lxb.expr.parse.lex.ItemType;
import com.lxb.expr.parse.lex.Lexer;

public class LexFunc implements StateFn {
    @Override
    public StateFn apply(Lexer lexer) {
        while (true) {
            char ch = lexer.next();
            if (!Character.isLetter(ch)) {
                lexer.backup();
                if (lexer.input.substring(lexer.start, lexer.pos).equals("expr")) {
                    lexer.emit(ItemType.itemExpr);
                    return new LexItem();
                }
                lexer.emit(ItemType.itemFunc);
                return new LexItem();
            }
        }
    }
}
