package com.lxb.expr.parse.node;

import com.lxb.error.Error;
import com.lxb.expr.parse.lex.Item;
import com.lxb.expr.parse.parse.Tags;
import com.lxb.expr.parse.parse.Tree;
import com.lxb.expr.parse.visitor.Visitor;
import com.lxb.models.FuncType;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
@Data
public class BinaryNode implements Node{
    private NodeType   nodeType;
    private int        pos;
    private List<Node> args;
    private Item       operator;
    private String     opStr;

    public BinaryNode(Item operator, Node arg1, Node arg2) {
        this.nodeType = NodeType.NodeBinary;
        this.pos = operator.pos;
        this.args = Arrays.asList(arg1, arg2);
        this.operator = operator;
        this.opStr = operator.val;
    }

    public String string() {
        return String.format("%s %s %s", args.get(0), operator.val, args.get(1));
    }

    public String stringAST() {
        return String.format("%s(%s %s)", operator.val, args.get(0).stringAST(), args.get(1).stringAST());
    }

    public Error check(Tree tree) {
        FuncType t1 = args.get(0).returnType();
        FuncType t2 = args.get(1).returnType();
        if (!(t1 == FuncType.TypeSeriesSet || t1 == FuncType.TypeNumberSet || t1 == FuncType.TypeScalar)) {
            return () -> String.format("expected NumberSet, SeriesSet, or Scalar, got %s", t1);
        }
        if (!(t2 == FuncType.TypeSeriesSet || t2 == FuncType.TypeNumberSet || t2 == FuncType.TypeScalar)) {
            return () -> String.format("expected NumberSet, SeriesSet, or Scalar, got %s", t2);
        }
        Error err = args.get(0).check(tree);
        if (err != null) {
            return err;
        }
        err = args.get(1).check(tree);
        if (err != null) {
            return err;
        }
        Tags.TagsAndError tags1 = args.get(0).tags();
        if (tags1.getError() != null) {
            return tags1.getError();
        }

        Tags.TagsAndError tags2 = args.get(1).tags();
        if (tags2.getError() != null) {
            return tags2.getError();
        }
        if (tags1.getTags() != null && tags2.getTags() != null && !tags2.getTags().keySet().containsAll(tags1.getTags().keySet())
                && !tags1.getTags().keySet().containsAll(tags2.getTags().keySet())) {
            return () -> String.format("parse: incompatible tags (%s and %s) in %s", tags1.getTags(), tags2.getTags(), this);
        }
        return null;
    }

    public FuncType returnType() {
        FuncType t0 = args.get(0).returnType();
        FuncType t1 = args.get(1).returnType();
        if (t1.ordinal() > t0.ordinal()) {
            return t1;
        }
        return t0;
    }

    public Tags.TagsAndError tags() {
        Tags.TagsAndError tags = args.get(0).tags();
        if (tags.error != null) {
            return tags;
        }
        if (tags.tags == null) {
            return args.get(1).tags();
        }
        return tags;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
