package com.lxb.expr.parse.parse;

import com.lxb.error.Error;
import com.lxb.expr.parse.lex.Item;
import com.lxb.expr.parse.lex.ItemType;
import com.lxb.expr.parse.lex.Lexer;
import com.lxb.expr.parse.node.BinaryNode;
import com.lxb.expr.parse.node.ExprNode;
import com.lxb.expr.parse.node.FuncNode;
import com.lxb.expr.parse.node.Node;
import com.lxb.expr.parse.node.NumberNode;
import com.lxb.expr.parse.node.PrefixNode;
import com.lxb.expr.parse.node.StringNode;
import com.lxb.expr.parse.node.UnaryNode;
import com.sun.org.apache.xpath.internal.ExpressionNode;

import java.util.Map;

public class Tree {
    public  String            text;
    public  Node              root;
    public  Map<String, Func> funcs;
    public  boolean           mapExpr;
    private Lexer             lex;
    private Item[]            token;
    private int               peekCount;

    public Tree(String text, Map<String, Func> funcs) {
        this.text = text;
        this.funcs = funcs;
        this.token = new Item[1];
    }

    public void parse() {
        root = O();
        expect(ItemType.itemEOF, "root input");
    }

    Node O() {
        Node n = A();
        while (true) {
            switch (peek().typ) {
                case itemOr:
                    n = new BinaryNode(next(), n, A());
                    break;
                default:
                    return n;
            }
        }
    }

    Node A() {
        Node n = C();
        while (true) {
            switch (peek().typ) {
                case itemAnd:
                    n = new BinaryNode(next(), n, C());
                    break;
                default:
                    return n;
            }
        }


    }

    Node C() {
        Node n = P();
        while (true) {
            Item peek = peek();
            switch (peek.typ) {
                case itemEq:
                case itemNotEq:
                case itemGreater:
                case itemGreaterEq:
                case itemLess:
                case itemLessEq:
                    n = new BinaryNode(next(), n, P());
                    break;
                default:
                    return n;
            }
        }


    }

    Node P() {
        Node n = M();
        while (true) {
            Item peek = peek();
            switch (peek.typ) {
                case itemPlus:
                case itemMinus:
                    n = new BinaryNode(next(), n, M());
                    break;
                default:
                    return n;
            }


        }
    }

    Node M() {
        Node n = E();
        while (true) {
            Item peek = peek();
            switch (peek.typ) {
                case itemMult:
                case itemDiv:
                case itemMod:
                    n = new BinaryNode(next(), n, E());
                    break;
                default:
                    return n;
            }
        }
    }

    Node E() {
        Node n = F();
        while (true) {
            Item peek = peek();
            switch (peek.typ) {
                case itemPow:
                    n = new BinaryNode(next(), n, F());
                    break;
                default:
                    return n;
            }
        }
    }

    Node F() {
        Item token = peek();
        switch (token.typ) {
            case itemNumber:
            case itemFunc:
                return v();
            case itemNot:
            case itemMinus:
                return new UnaryNode(next(), F());
            case itemPrefix:
                Item item = next();
                return new PrefixNode(item.val, item.pos, F());
            case itemLeftParen:
                next();
                Node n = O();
                expect(ItemType.itemRightParen, "input: F()");
                return n;
            default:
                unexpected(token, "input: F()");
        }
        return null;
    }

    private Node v() {
        Item token = next();
        switch (token.typ) {
            case itemNumber:
                return new NumberNode(token.pos, token.val);
            case itemFunc:
                backup();
                return Func();
            default:
                unexpected(token, "input: v()");
        }
        return null;
    }

    public FuncNode Func() {
        Item     token    = next();
        Func     function = getFunction(token.val);
        FuncNode f        = new FuncNode(token.pos, token.val, function);
        expect(ItemType.itemLeftParen, "func");
        while (true) {
            token = next();
            switch (token.typ) {
                case itemTripleQuotedString:
                    f.append(new StringNode(token.pos, token.val, token.val.substring(3, token.val.length() - 3)));
                    break;
                case itemString:
                    f.append(new StringNode(token.pos, token.val, token.val.substring(1, token.val.length() - 1)));
                    break;
                case itemRightParen:
                    break;
                case itemExpr:
                    expect(ItemType.itemLeftParen, "v() expect left paran in itemExpr");
                    int start = lex.lastPos;
                    int leftCount = 1;

                    TOKENS:
                    while (true) {
                        Item t = next();
                        switch (t.typ) {
                            case itemLeftParen:
                                leftCount++;
                                break;
                            case itemFunc:
                                break;
                            case itemRightParen:
                                leftCount--;
                                if (leftCount == 0) {
                                    expect(ItemType.itemRightParen, "v() expect right paren in itemExpr");
                                    backup();
                                    break TOKENS;
                                }
                            case itemEOF:
                                unexpected(t, "input: v()");
                                break;
                            default:
                        }
                    }
                    ExprNode exprNode = new ExprNode(lex.input.substring(start, lex.lastPos), lex.pos);

                    //TODO parsesub
                    f.append(exprNode);
                    break;
            }
            Item tk = next();
            switch (tk.typ) {
                case itemComma:
                    break;
                case itemRightParen:
                    break;
                default:
                    unexpected(tk, "func");
            }
        }
    }

    public Func getFunction(String name) {
        return funcs.get(name);
    }

    public Item expect(ItemType expected, String context) {
        Item token = next();
        if (token.typ != expected) {
            unexpected(token, context);
        }
        return token;
    }

    private void unexpected(Item token, String context) {
        throw new RuntimeException(String.format("unexpected %s in %s", token, context));
    }

    public Item next() {
        if (peekCount > 0) {
            peekCount--;
        } else {
            token[0] = lex.nextItem();
        }
        return token[peekCount];
    }

    public void backup() {
        peekCount++;
    }

    public Item peek() {
        if (peekCount > 0) {
            return token[peekCount - 1];
        }
        peekCount = 1;
        token[0] = lex.nextItem();
        return token[0];
    }
}
