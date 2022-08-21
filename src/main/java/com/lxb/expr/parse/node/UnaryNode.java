package com.lxb.expr.parse.node;

import com.lxb.error.Error;
import com.lxb.expr.parse.lex.Item;
import com.lxb.expr.parse.parse.Tags;
import com.lxb.expr.parse.parse.Tree;
import com.lxb.models.FuncType;
import lombok.Getter;

@Getter
public class UnaryNode implements Node {
    private NodeType nodeType;
    private int      pos;
    private Node     arg;
    private Item     operator;
    private String   opStr;

    public UnaryNode(Item operator, Node arg) {
        this.nodeType = NodeType.NodeUnary;
        this.pos = operator.pos;
        this.arg = arg;
        this.operator = operator;
        this.opStr = operator.val;
    }

    public String string() {
        return String.format("%s%s", operator.val, arg);
    }

    public String stringAST() {
        return String.format("%s(%s)", operator.val, arg);
    }

    public Error check(Tree tree) {
        FuncType returnType = arg.returnType();
        switch (returnType) {
            case TypeNumberSet:
            case TypeSeriesSet:
            case TypeScalar:
                return arg.check(tree);
            default:
                return () -> String.format("parse: type error in %s, expected %s, got %s", this, "number", returnType);
        }
    }

    public FuncType returnType() {
        return arg.returnType();
    }

    public Tags.TagsAndError tags() {
        return arg.tags();
    }
}
