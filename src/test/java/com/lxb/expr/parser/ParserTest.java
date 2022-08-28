package com.lxb.expr.parser;

import com.lxb.expr.parse.lex.Lexer;
import com.lxb.expr.parse.parse.Tree;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class ParserTest {
    @Test
    public void testNumber() {
        baseTest("1");
    }

    @Test
    public void testFunction(){
        baseTest("avg(q(\"test\", \"1m\"))");
    }

    @Test
    public void testAddition() {
        baseTest("1 + 2");
    }


    @Test
    public void testExpression() {

        baseTest("1+2*3/4-5 && !2|| -4");
    }
    @Test
    public void testExpressionWithFunc(){
        baseTest("avg(q(\"q\", \"1m\"))>=0.7&&avg(q(\"q\", \"1m\"))!=3-0x8");
    }
    @Test
    public void testFuncType(){
        baseTest("avg(q(\"q\", \"1m\"))>avg(q(\"q\", \"1m\"))+avg(q(\"q\", \"1m\"))");
    }
    @Test
    public void testSeriesCompare(){
        baseTest("q(\"q\", \"1m\")>0");
    }
    @Test
    public void testUnarySeries(){
        baseTest("!q(\"q\", \"1m\")");
    }
    @Test
    public void testExprInFunc(){
        baseTest("forecastlr(q(\"q\", \"1m\"), -1)");
    }

    @Test
    public void testNextFunction(){
        baseTest("avg(");
    }
    @Test
    public void testEmpty(){
        baseTest("");
    }


    private void baseTest(String s) {
        Tree tree = new Tree(s, new HashMap<>());
        tree.parse();
        System.out.println(tree.root.stringAST());
    }
    private void baseTest2String(String s) {
        Tree tree = new Tree(s, new HashMap<>());
        tree.parse();
        System.out.println(tree.root);
    }

    private void testLex(String s) {
        Lexer lexer = new Lexer(s);
        System.out.println(lexer.items);
    }


}
