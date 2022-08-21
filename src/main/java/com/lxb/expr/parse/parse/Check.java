package com.lxb.expr.parse.parse;

import com.lxb.expr.parse.node.FuncNode;

public interface Check {
    Error apply(Tree tree, FuncNode funcNode);
}
