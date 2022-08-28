package com.lxb.expr.parse.node;

import com.lxb.error.Error;
import com.lxb.expr.parse.parse.Tags;
import com.lxb.expr.parse.parse.Tree;
import com.lxb.expr.parse.visitor.Visitor;
import com.lxb.models.FuncType;
import lombok.Data;

@Data
public class ExprNode implements Node {
    public  NodeType nodeType;
    private int      pos;
    private String   text;
    private Tree     tree;

    public ExprNode(String text, int pos) {
        this.nodeType = NodeType.NodeExpr;
        this.pos = pos;
        this.text = text;
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
        FuncType funcType = tree.root.returnType();
        switch (funcType) {
            case TypeNumberSet:
            case TypeScalar:
                return FuncType.TypeNumberExpr;
            case TypeSeriesExpr:
                return FuncType.TypeSeriesExpr;
            default:
                return FuncType.TypeUnexpected;
        }
    }

    public Tags.TagsAndError tags() {
        return new Tags.TagsAndError();
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }


}
