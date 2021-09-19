public class Tester {
    public static void main(String[] args) {
        //new Tokenizer("WordAndDotGrammar", "Hello hello world World.");
    
        new Tokenizer("ArithmeticGrammar", "1+2+2");

        new Tokenizer("MaximalMunchGrammar", "1 1.2+2");
    }
}
