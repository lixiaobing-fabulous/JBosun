package com.lxb.expr.parse.lex;


import org.junit.jupiter.api.Test;

class LexerTest {
    @Test
    public void testEmpty() {
        testBase("");
    }

    @Test
    public void testSpaces() {
        testBase(" \t\n");
    }

    @Test
    public void testString() {
        testBase("\"now is the time\"");
    }

    @Test
    public void testNumbers() {
        testBase("1 02 0x14 7.2 1e3");
    }

    @Test
    public void testTrippleQuote() {
        testBase("'''select'''");
    }

    @Test
    public void testExpression() {
        testBase("avg(q(\"sum:sys.cpu.user{host=*-web*}\", \"1m\")) < 0.2 || avg(q(\"sum:sys.cpu.user{host=*-web*}\", \"1m\")) > 0.4");
    }

    @Test
    public void testExpressionWithTripleQuote() {
        testBase("influx(\"db\", '''select value from \"mymetric.name.with.dots\" where  \"key\" = 'single quoted value' and \"other_key\" = '' group by *''', \"1h\", \"\")");
    }

    @Test
    public void testUnclosedQuote() {
        testBase("\"");
    }

    @Test
    public void testSingleQuoteIsInvalid() {
        testBase("'single quote is invalid'");
    }

    @Test
    public void testUnclosedTrippleQuote() {
        testBase("''' unclosed triple quote ''");
    }

    private void testBase(String s) {
        Lexer lexer = new Lexer(s);
        System.out.println(lexer.items);
    }
}