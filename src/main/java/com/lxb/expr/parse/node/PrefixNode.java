package com.lxb.expr.parse.node;

import com.lxb.error.Error;
import com.lxb.expr.parse.parse.Tags;
import com.lxb.expr.parse.parse.Tree;
import com.lxb.expr.parse.visitor.Visitor;
import com.lxb.models.FuncType;
import lombok.Data;

@Data
public class PrefixNode implements Node {
    public NodeType nodeType;
    public int      pos;
    public Node     arg;
    public String   text;

    public PrefixNode(String text, int pos, Node arg) {
        this.nodeType = NodeType.NodePrefix;
        this.pos = pos;
        this.arg = arg;
        this.text = text;
    }

    public String string() {
        return String.format("%s%s", text, arg);
    }

    public String stringAST() {
        return string();
    }

    public Error check(Tree tree) {
        if (arg.getNodeType() != NodeType.NodeFunc) {
            return () -> "parse: prefix on non-function";
        }
        if (!((FuncNode) this.arg).f.prefixEnabled) {
            return () -> String.format("func %s does not support a prefix", ((FuncNode) arg).name);
        }
        return arg.check(tree);
    }

    public FuncType returnType() {
        return arg.returnType();
    }

    public Tags.TagsAndError tags() {
        return arg.tags();
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
