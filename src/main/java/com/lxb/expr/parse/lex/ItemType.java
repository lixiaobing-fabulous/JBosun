package com.lxb.expr.parse.lex;

public enum ItemType {
    itemError,
    itemEOF,
    itemNot,       // '!'
    itemAnd,       // '&&'
    itemOr,        // '||'
    itemGreater,   // '>'
    itemLess,      // '<'
    itemGreaterEq, // '>='
    itemLessEq,    // '<='
    itemEq,        // '=='
    itemNotEq,     // '!='
    itemPlus,      // '+'
    itemMinus,     // '-'
    itemMult,      // '*'
    itemDiv,       // '/'
    itemMod,       // '%'
    itemNumber,    // simple number
    itemComma,
    itemLeftParen,
    itemRightParen,
    itemString,
    itemFunc,
    itemTripleQuotedString,
    itemPow, // '**'
    itemExpr,
    itemPrefix // [prefix]

}
