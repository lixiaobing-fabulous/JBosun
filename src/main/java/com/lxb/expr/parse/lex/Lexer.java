package com.lxb.expr.parse.lex;

import com.lxb.expr.parse.lex.stateFn.LexItem;
import com.lxb.expr.parse.lex.stateFn.StateFn;

import java.util.LinkedList;
import java.util.Queue;

public class Lexer {
    public String  input;
    public StateFn state;
    public int     pos;
    public int         start;
    public int         lastPos;
    public Queue<Item> items;

    public char next() {
        if (pos >= input.length()) {
            return (char) -1;
        }
        return input.charAt(pos++);
    }

    public char peek() {
        return input.charAt(pos);
    }

    public void backup() {
        if (this.pos == this.input.length()) {
            return;
        }
        this.pos--;
    }

    public void emit(ItemType t) {
        items.offer(new Item(t, start, input.substring(start, pos)));
        start = pos;
    }

    public boolean accept(String valid) {
        if (valid.indexOf(next()) >= 0) {
            return true;
        }
        backup();
        return false;
    }

    public void acceptRun(String valid) {
        while (valid.indexOf(next()) >= 0) {
        }
        backup();
    }

    public void ignore() {
        this.start = this.pos;
    }

    public int lineNumber() {
        return this.input.substring(0, lastPos).split("\n").length + 1;
    }

    public StateFn errorf(String format, Object... args) {
        this.items.add(new Item(ItemType.itemError, start, String.format(format, args)));
        return null;
    }

    public Item nextItem() {
        Item item = items.poll();
        lastPos = item.pos;
        return item;
    }

    public Lexer(String input) {
        this.input = input;
        this.items = new LinkedList<>();
        for (state = new LexItem(); state != null; ) {
            this.state = this.state.apply(this);
        }
    }

    public boolean scanNumber() {
        String digits = "0123456789";
        if (accept("0") && accept("xX")) {
            digits = "0123456789abcdefABCDEF";
        }
        acceptRun(digits);
        if (accept(".")) {
            acceptRun(digits);
        }
        if (accept("eE")) {
            accept("+-");
            acceptRun("0123456789");
        }
        return true;
    }


}
