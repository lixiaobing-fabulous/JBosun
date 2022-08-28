package com.lxb.expr.parse.visitor;

import com.lxb.expr.parse.lex.Item;
import com.lxb.expr.parse.node.BinaryNode;
import com.lxb.expr.parse.node.ExprNode;
import com.lxb.expr.parse.node.FuncNode;
import com.lxb.expr.parse.node.Node;
import com.lxb.expr.parse.node.NumberNode;
import com.lxb.expr.parse.node.PrefixNode;
import com.lxb.expr.parse.node.StringNode;
import com.lxb.expr.parse.node.UnaryNode;
import com.lxb.expr.parse.visitor.functions.Function;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Evaluator implements Visitor {
    private Map<String, Function> functions;

    public Evaluator(Map<String, Function> functions) {
        this.functions = functions;
    }

    @Override
    public Object visit(Node node) {
        System.out.println("默认不访问");
        return null;
    }

    @Override
    public Object visit(BinaryNode node) {
        List<Node> args     = node.getArgs();
        Node       node1    = args.get(0);
        Node       node2    = args.get(1);
        Object     arg1     = node1.accept(this);
        Object     arg2     = node2.accept(this);
        Item       operator = node.getOperator();
        switch (operator.typ) {
            case itemPlus:
                if (arg1 instanceof String || arg2 instanceof String) {
                    return (Double) arg1 + (Double) arg2;
                }
                if (arg1 instanceof Double || arg2 instanceof Double) {
                    return (Double) arg1 + (Double) arg2;
                }
                if (arg1 instanceof Long || arg2 instanceof Long) {
                    return (Long) arg1 + (Long) arg2;
                }
                break;
            case itemDiv:
                if (arg1 instanceof Double || arg2 instanceof Double) {
                    return (Double) arg1 / (Double) arg2;
                }
                if (arg1 instanceof Long || arg2 instanceof Long) {
                    return (Long) arg1 / (Long) arg2;
                }
                break;
            case itemMinus:
                if (arg1 instanceof Double || arg2 instanceof Double) {
                    return (Double) arg1 - (Double) arg2;
                }
                if (arg1 instanceof Long || arg2 instanceof Long) {
                    return (Long) arg1 - (Long) arg2;
                }
                break;
            case itemMult:
                if (arg1 instanceof Double || arg2 instanceof Double) {
                    return (Double) arg1 * (Double) arg2;
                }
                if (arg1 instanceof Long || arg2 instanceof Long) {
                    return (Long) arg1 * (Long) arg2;
                }
                break;
            case itemMod:
                if (arg1 instanceof Long || arg2 instanceof Long) {
                    return (Long) arg1 % (Long) arg2;
                }
                break;
            case itemEq:
                return arg1.equals(args);
            case itemGreater:
                if (arg1 instanceof Comparable && arg2 instanceof Comparable) {
                    return ((Comparable) arg1).compareTo(arg2) > 0;
                }
                break;
            case itemGreaterEq:
                if (arg1 instanceof Comparable && arg2 instanceof Comparable) {
                    return ((Comparable) arg1).compareTo(arg2) >= 0;
                }
                break;
            case itemLess:
                if (arg1 instanceof Comparable && arg2 instanceof Comparable) {
                    return ((Comparable) arg1).compareTo(arg2) < 0;
                }
                break;
            case itemLessEq:
                if (arg1 instanceof Comparable && arg2 instanceof Comparable) {
                    return ((Comparable) arg1).compareTo(arg2) <= 0;
                }
                break;
            case itemOr:
                if (arg1 instanceof Boolean && arg2 instanceof Boolean) {
                    return (Boolean) arg1 || (Boolean) arg2;
                }
            case itemAnd:
                if (arg1 instanceof Boolean && arg2 instanceof Boolean) {
                    return (Boolean) arg1 && (Boolean) arg2;
                }

        }
        return null;
    }

    @Override
    public Object visit(ExprNode node) {
        return null;
    }

    @Override
    public Object visit(FuncNode node) {
        List<Node> args     = node.getArgs();
        Function   function = functions.get(node.name);
        Object[]   objects  = args.stream().map(n -> n.accept(this)).collect(Collectors.toList()).toArray();
        return function.apply(objects);
    }

    @Override
    public Object visit(NumberNode node) {
        return node.isLong ? node.getLongValue() : node.getDoubleValue();
    }

    @Override
    public Object visit(PrefixNode node) {
        return null;
    }

    @Override
    public Object visit(StringNode node) {
        return node.quoted;
    }

    @Override
    public Object visit(UnaryNode node) {
        Item   operator = node.getOperator();
        Node   arg      = node.getArg();
        Object value    = arg.accept(this);
        switch (operator.typ) {
            case itemNot:
                if (value instanceof Boolean) {
                    return !(Boolean) value;
                }
        }
        return null;
    }


}
