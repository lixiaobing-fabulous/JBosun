package com.lxb.expr.parse.visitor.functions;

public class Max implements Function {
    @Override
    public Object apply(Object... args) {
        return Double.max((Double) args[0], (Double) args[1]);
    }
}
