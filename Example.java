import Tokenizer.*;

public class Example {
    public static void main(String[] args) {

       
/*

        Grammar ArithmeticGrammar = new Grammar();
        ArithmeticGrammar.add(new TokenType("NUMBER", "^[0-9]+(\\.([0-9])+)?"));
        ArithmeticGrammar.add(new TokenType("ADD", "^[+]"));
        ArithmeticGrammar.add(new TokenType("MUL", "^[*]"));

        Grammar MaximalMunchGrammar = new Grammar();
        MaximalMunchGrammar.add(new TokenType("FLOAT", "^[0-9]+\\.[0-9]+"));
        MaximalMunchGrammar.add(new TokenType("INTEGER", "^[0-9]+"));
*/


        Grammar WordAndDotGrammar = new Grammar();
        WordAndDotGrammar.add(new TokenType("WORD", "[A-ZÅÄÖa-zåäö]+"));
        WordAndDotGrammar.add(new TokenType("DOT", "[.]"));

        Tokenizer t = new Tokenizer(WordAndDotGrammar, "Hello world.");

        t.next();
        t.next();
        t.next();
        t.back();
        t.next();


        // This example gives the following output:
        /**
         * WORD(Hello)
         * Word(world)
         * DOT(.)
         * END()
         * DOT(.)
         * END()
         */
    }
}
