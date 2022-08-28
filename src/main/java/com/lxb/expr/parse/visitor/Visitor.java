package com.lxb.expr.parse.visitor;

import com.lxb.expr.parse.node.BinaryNode;
import com.lxb.expr.parse.node.ExprNode;
import com.lxb.expr.parse.node.FuncNode;
import com.lxb.expr.parse.node.Node;
import com.lxb.expr.parse.node.NumberNode;
import com.lxb.expr.parse.node.PrefixNode;
import com.lxb.expr.parse.node.StringNode;
import com.lxb.expr.parse.node.UnaryNode;

public interface Visitor {
    Object visit(Node node);

    Object visit(BinaryNode node);

    Object visit(ExprNode node);

    Object visit(FuncNode node);

    Object visit(NumberNode node);

    Object visit(PrefixNode node);

    Object visit(StringNode node);

    Object visit(UnaryNode node);
}
