package com.lxb.expr.parse.node;

import com.lxb.error.Error;
import com.lxb.expr.parse.parse.Func;
import com.lxb.expr.parse.parse.Tags;
import com.lxb.expr.parse.parse.Tree;
import com.lxb.expr.parse.visitor.Visitor;
import com.lxb.models.FuncType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FuncNode implements Node {
    public  NodeType   nodeType;
    public  int        pos;
    public  String     name;
    public  Func       f;
    public  List<Node> args;
    private String     prefix;

    public FuncNode(int pos, String name, Func f) {
        this.nodeType = NodeType.NodeFunc;
        this.pos = pos;
        this.name = name;
        this.f = f;
        this.args = new ArrayList<>();
    }

    public void append(Node arg) {
        this.args.add(arg);
    }

    @Override
    public String string() {
        StringBuilder str = new StringBuilder(name + "(");
        for (int i = 0; i < args.size(); i++) {
            if (i > 0) {
                str.append(",");
            }
            str.append(args.get(i).string());
        }
        str.append(")");
        return str.toString();
    }

    public String stringAST() {
        StringBuilder str = new StringBuilder(name + "(");
        for (int i = 0; i < args.size(); i++) {
            if (i > 0) {
                str.append(",");
            }
            str.append(args.get(i).stringAST());
        }
        str.append(")");
        return str.toString();
    }

    public FuncType returnType() {
        return f.returnType;
    }

    public Tags.TagsAndError tags() {
        if (f.tags == null) {
            return new Tags.TagsAndError();
        }
        return f.tags.apply(args);
    }

    public Error check(Tree tree) {
        return null;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
