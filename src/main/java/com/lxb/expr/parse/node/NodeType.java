package com.lxb.expr.parse.node;

public enum NodeType {
    NodeFunc,                   // A function call.
    NodeBinary,                 // Binary operator: math, logical, compare
    NodeUnary,                  // Unary operator: !, -
    NodeString,                 // A string constant.
    NodeNumber,                 // A numerical constant.
    NodeExpr,                  // A sub expression
    NodePrefix                 // A host prefix [""]

}
