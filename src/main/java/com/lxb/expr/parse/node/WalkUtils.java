package com.lxb.expr.parse.node;

import javax.swing.*;
import java.util.function.Consumer;

public class WalkUtils {
    public static void walk(Node node, Consumer<Node> fun) {
        fun.accept(node);
        if (node instanceof BinaryNode) {
            walk(((BinaryNode) node).getArgs().get(0), fun);
            walk(((BinaryNode) node).getArgs().get(1), fun);
        } else if (node instanceof FuncNode) {
            for (Node arg : ((FuncNode) node).args) {
                walk(arg, fun);
            }
        } else if (node instanceof UnaryNode) {
            walk(((UnaryNode) node).getArg(), fun);
        } else if (node instanceof NumberNode || node instanceof StringNode || node instanceof ExprNode) {

        } else {
            throw new RuntimeException(String.format("other type: %s", node.getNodeType()));
        }

    }
}
