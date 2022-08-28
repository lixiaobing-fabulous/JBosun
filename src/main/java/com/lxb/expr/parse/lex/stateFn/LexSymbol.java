package com.lxb.expr.parse.lex.stateFn;

import com.lxb.expr.parse.lex.ItemType;
import com.lxb.expr.parse.lex.Lexer;

import static com.lxb.expr.parse.lex.LexUtils.SYMBOLS;

public class LexSymbol implements StateFn {
    @Override
    public StateFn apply(Lexer lexer) {
        lexer.backup();
        lexer.acceptRun(SYMBOLS);
        String symbol = lexer.input.substring(lexer.start, lexer.pos);
        switch (symbol) {
            case "!":
                lexer.emit(ItemType.itemNot);
                break;
            case "&&":
                lexer.emit(ItemType.itemAnd);
                break;
            case "||":
                lexer.emit(ItemType.itemOr);
                break;
            case ">":
                lexer.emit(ItemType.itemGreater);
                break;
            case "<":
                lexer.emit(ItemType.itemLess);
                break;
            case ">=":
                lexer.emit(ItemType.itemGreaterEq);
                break;
            case "<=":
                lexer.emit(ItemType.itemLessEq);
                break;
            case "==":
                lexer.emit(ItemType.itemEq);
                break;
            case "!=":
                lexer.emit(ItemType.itemNotEq);
                break;
            case "+":
                lexer.emit(ItemType.itemPlus);
                break;
            case "-":
                lexer.emit(ItemType.itemMinus);
                break;
            case "*":
                lexer.emit(ItemType.itemMult);
                break;
            case "**":
                lexer.emit(ItemType.itemPow);
                break;
            case "/":
                lexer.emit(ItemType.itemDiv);
                break;
            case "%":
                lexer.emit(ItemType.itemMod);
                break;
            default:
                lexer.emit(ItemType.itemError);
        }
        return new LexItem();
    }

}
