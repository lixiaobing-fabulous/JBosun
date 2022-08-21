package com.lxb.expr.parse.lex.stateFn;

import com.lxb.expr.parse.lex.ItemType;
import com.lxb.expr.parse.lex.LexUtils;
import com.lxb.expr.parse.lex.Lexer;

public class LexItem implements StateFn {
    @Override
    public StateFn apply(Lexer lexer) {
        while (true) {
            char ch = lexer.next();
            if (LexUtils.isSymbol(ch)) {
                return new LexSymbol();
            } else if (LexUtils.isNumber(ch)) {
                lexer.backup();
                return new LexNumber();
            } else if (Character.isLetter(ch)) {
                return new LexFunc();
            } else if (ch == '(') {
                lexer.emit(ItemType.itemLeftParen);
            } else if (ch == ')') {
                lexer.emit(ItemType.itemRightParen);
            } else if (ch == '[') {
                return new LexPrefixBegin();
            } else if (ch == '"') {
                return new LexString();
            } else if (ch == '\'') {
                return new LexStringTripleEnd();
            } else if (ch == ',') {
                lexer.emit(ItemType.itemComma);
            } else if (LexUtils.isSpace(ch)) {
                lexer.ignore();
            } else if (ch == LexUtils.EOF) {
                lexer.emit(ItemType.itemEOF);
                break;
            } else {
                return lexer.errorf("invalid character: %s", ch);
            }
        }
        return null;
    }

}
