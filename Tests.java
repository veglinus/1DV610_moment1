import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import Tokenizer.*;

public class Tests {

    Grammar WordAndDotGrammar = new Grammar();
    Grammar ArithmeticGrammar = new Grammar();

    @Before
    public void populate() {
        WordAndDotGrammar.add(new TokenType("WORD", "[A-ZÅÄÖa-zåäö]+"));
        WordAndDotGrammar.add(new TokenType("DOT", "[.]"));
        ArithmeticGrammar.add(new TokenType("NUMBER", "^[0-9]+(\\.([0-9])+)?"));
        ArithmeticGrammar.add(new TokenType("ADD", "^[+]"));
        ArithmeticGrammar.add(new TokenType("MUL", "^[*]"));
    }

    @Test
    public void TC1() {
        Tokenizer t = new Tokenizer(WordAndDotGrammar, "a");
        Token result = new Token("WORD", "a");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }

    @Test
    public void TC2() {
        Tokenizer t = new Tokenizer(WordAndDotGrammar, "a aa");
        t.next();
        Token result = new Token("WORD", "aa");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }

    @Test
    public void TC3() {
        Tokenizer t = new Tokenizer(WordAndDotGrammar, "a.b");
        t.next();
        Token result = new Token("DOT", ".");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }

    @Test
    public void TC4() {
        Tokenizer t = new Tokenizer(WordAndDotGrammar, "a.b");
        t.next();
        t.next();
        Token result = new Token("WORD", "b");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }

    @Test
    public void TC5() {
        Tokenizer t = new Tokenizer(WordAndDotGrammar, "aa. b");
        t.next();
        t.next();
        Token result = new Token("WORD", "b");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }

    @Test
    public void TC6() {
        Tokenizer t = new Tokenizer(WordAndDotGrammar, "a .b");
        t.next();
        t.next();
        t.back();
        Token result = new Token("DOT", ".");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }

    @Test
    public void TC7() {
        Tokenizer t = new Tokenizer(WordAndDotGrammar, "");
        Token result = new Token("END", "");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }

    @Test
    public void TC8() {
        Tokenizer t = new Tokenizer(WordAndDotGrammar, " ");
        Token result = new Token("END", "");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }

    @Test
    public void TC9() {
        Tokenizer t = new Tokenizer(WordAndDotGrammar, "a");
        t.next();
        Token result = new Token("END", "");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }

    @Test
    public void TC10() {
        Tokenizer t = new Tokenizer(WordAndDotGrammar, "a");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            t.back();
        });
    }

    /*
    @Test
    public void TC11() {
        Tokenizer t = new Tokenizer(WordAndDotGrammar, "!");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            t.getActiveToken();
        });

        String errorMessage = exception.getMessage();
        Assertions.assertTrue(errorMessage.contains("No lexical element matches input."));
    }*/


    @Test
    public void TC12() {
        Tokenizer t = new Tokenizer(ArithmeticGrammar, "3");
        Token result = new Token("NUMBER", "3");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }

    @Test
    public void TC13() {
        Tokenizer t = new Tokenizer(ArithmeticGrammar, "3.14");
        Token result = new Token("NUMBER", "3.14");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }

    @Test
    public void TC14() {
        Tokenizer t = new Tokenizer(ArithmeticGrammar, "3 + 54 * 4");
        t.next();
        t.next();
        t.next();
        Token result = new Token("MUL", "*");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }

    @Test
    public void TC15() {
        Tokenizer t = new Tokenizer(ArithmeticGrammar, "3+5 # 4");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            t.next();
            t.next();
            t.next();
        });
    }

    @Test
    public void TC16() {
        Tokenizer t = new Tokenizer(ArithmeticGrammar, "3.0+54.1     + 4.2");
        t.next();
        t.back();
        t.next();
        t.next();
        t.next();
        Token result = new Token("ADD", "+");
        Assertions.assertEquals(result.toString(), t.getActiveToken().toString());
    }
}
