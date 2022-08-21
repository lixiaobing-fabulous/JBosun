package com.lxb.expr.parse.lex;

import lombok.Data;

@Data
public class Item {
    public ItemType typ;
    public int      pos;
    public String   val;

    public String string() {
        if (typ == ItemType.itemEOF) {
            return "EOF";
        } else {
            return val;
        }
    }

    public Item(ItemType typ, int pos, String val) {
        this.typ = typ;
        this.pos = pos;
        this.val = val;
    }
}
