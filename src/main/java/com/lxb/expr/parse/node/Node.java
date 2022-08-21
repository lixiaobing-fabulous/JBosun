package com.lxb.expr.parse.node;

import com.lxb.error.Error;
import com.lxb.expr.parse.parse.Tags;
import com.lxb.expr.parse.parse.Tree;
import com.lxb.models.FuncType;

public interface Node {
    NodeType getNodeType();

    String string();

    String stringAST();


    Error check(Tree tree);

    FuncType returnType();

    Tags.TagsAndError tags();

}
