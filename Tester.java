import Tokenizer.*;

public class Tester {
    public static void main(String[] args) {

       
        Grammar WordAndDotGrammar = new Grammar();
        WordAndDotGrammar.add(new TokenType("WORD", "[A-ZÅÄÖa-zåäö]+"));
        WordAndDotGrammar.add(new TokenType("DOT", "[.]"));

        Grammar ArithmeticGrammar = new Grammar();
        ArithmeticGrammar.add(new TokenType("NUMBER", "^[0-9]+(\\.([0-9])+)?"));
        ArithmeticGrammar.add(new TokenType("ADD", "^[+]"));
        ArithmeticGrammar.add(new TokenType("MUL", "^[*]"));

        Grammar MaximalMunchGrammar = new Grammar();
        MaximalMunchGrammar.add(new TokenType("FLOAT", "^[0-9]+\\.[0-9]+"));
        MaximalMunchGrammar.add(new TokenType("INTEGER", "^[0-9]+"));

        //Tokenizer t = new Tokenizer(WordAndDotGrammar, "Hello world.");

        Tokenizer t = new Tokenizer(ArithmeticGrammar, "3.0+54.1     + 4.2");

        t.next();
        t.back();

        t.next();
        t.next();
        t.next();


        // This example gives the following output:
        /**
         * WORD(Hello)
         * Word(world)
         * DOT(.)
         */
    }
}
