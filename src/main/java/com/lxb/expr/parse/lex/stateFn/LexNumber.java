package com.lxb.expr.parse.lex.stateFn;

import com.lxb.expr.parse.lex.ItemType;
import com.lxb.expr.parse.lex.Lexer;

public class LexNumber implements StateFn {
    @Override
    public StateFn apply(Lexer lexer) {
        lexer.scanNumber();
        lexer.emit(ItemType.itemNumber);
        return new LexItem();
    }
}
