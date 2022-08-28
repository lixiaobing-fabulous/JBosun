package com.lxb.expr.parse.node;

import com.lxb.error.Error;
import com.lxb.expr.parse.parse.Tags;
import com.lxb.expr.parse.parse.Tree;
import com.lxb.expr.parse.visitor.Visitor;
import com.lxb.models.FuncType;
import lombok.Data;

@Data
public class NumberNode implements Node {
    public  NodeType nodeType;
    public  int      pos;
    public  boolean  isLong;
    private boolean  isDouble;
    private Long     longValue;
    private Double   doubleValue;
    private String   text;

    public NumberNode(int pos, String text) {
        this.nodeType = NodeType.NodeNumber;
        this.pos = pos;
        this.text = text;
        try {
            longValue = Long.valueOf(text);
            isLong = true;
            return;
        } catch (NumberFormatException e) {
            isLong = false;
        }
        try {
            doubleValue = Double.valueOf(text);
            isDouble = true;
        } catch (NumberFormatException e) {
            isDouble = false;
        }
    }

    public String string() {
        return text;
    }

    public String stringAST() {
        return string();
    }

    public Error check(Tree tree) {
        return null;
    }

    public FuncType returnType() {
        return FuncType.TypeScalar;
    }

    public Tags.TagsAndError tags() {
        return new Tags.TagsAndError();
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
