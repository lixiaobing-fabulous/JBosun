package com.lxb.expr.parse.node;

import com.lxb.error.Error;
import com.lxb.expr.parse.parse.Tags;
import com.lxb.expr.parse.parse.Tree;
import com.lxb.expr.parse.visitor.Visitor;
import com.lxb.models.FuncType;
import lombok.Data;

@Data
public class StringNode implements Node {
    public NodeType nodeType;
    public int      pos;
    public String   quoted;
    public String   text;

    public StringNode(int pos, String quoted, String text) {
        this.nodeType = NodeType.NodeString;
        this.pos = pos;
        this.quoted = quoted;
        this.text = text;
    }

    public String string() {
        return this.quoted;
    }

    public String stringAST() {
        return this.quoted;
    }

    public Error check(Tree tree) {
        return null;
    }

    public FuncType returnType() {
        return FuncType.TypeString;
    }

    public Tags.TagsAndError tags() {
        return new Tags.TagsAndError();
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
