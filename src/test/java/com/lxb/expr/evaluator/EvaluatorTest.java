package com.lxb.expr.evaluator;

import com.lxb.expr.parse.parse.Tree;
import com.lxb.expr.parse.visitor.Evaluator;
import com.lxb.expr.parse.visitor.functions.Function;
import com.lxb.expr.parse.visitor.functions.Max;
import com.lxb.expr.parse.visitor.functions.Print;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class EvaluatorTest {
    @Test
    public void test(){
//        Tree tree = new Tree("1+(1+2)*10 > 10 && 1 < 0", new HashMap<>());
//        Tree tree = new Tree("print(1)", new HashMap<>());
        Tree tree = new Tree("max(1, 1+1)", new HashMap<>());
        tree.parse();
        HashMap<String, Function> functions = new HashMap<>();
        functions.put("print", new Print());
        functions.put("max", new Max());
        Evaluator evaluator = new Evaluator(functions);
        System.out.println(tree.root.accept(evaluator));
    }
}
