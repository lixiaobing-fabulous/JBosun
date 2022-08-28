package com.lxb.expr.parse.visitor.functions;

public class Print implements Function {

    @Override
    public Object apply(Object... args) {
        System.out.println(args[0]);
        return null;
    }
}
