public class Tester {
    public static void main(String[] args) {

        Grammar test = new Grammar();
       
        /*
        test.add(new TokenType("WORD", "[A-ZÅÄÖa-zåäö]+"));
        test.add(new TokenType("DOT", "[.]"));

        Tokenizer t = new Tokenizer(test, "Hello world.");

        t.next();
        t.next();
        t.next();
        t.next();
        t.back();
        t.back();
        */

        /*
        test.add(new TokenType("FLOAT", "^[0-9]+\\.[0-9]+"));
        test.add(new TokenType("INTEGER", "^[0-9]+"));

        Tokenizer t = new Tokenizer(test, "3.14 5 335");

        t.next();
        t.next();
        t.next();
        t.next();
        */

        test.add(new TokenType("FLOAT", "^[0-9]+\\.[0-9]+"));

        Tokenizer t = new Tokenizer(test, "3.14 Hej! 4");

        t.next();
        t.next();



        //System.out.println(result.value);

    }
}
