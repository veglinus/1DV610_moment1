public class Tester {
    public static void main(String[] args) {

        Grammar test = new Grammar();
       
        test.add(new TokenType("WORD", "[A-ZÅÄÖa-zåäö]+"));
        test.add(new TokenType("DOT", "[.]"));

        Tokenizer t = new Tokenizer(test, "Hello world.");

        t.next();
        t.next();
        t.next();
        t.next();
        t.back();
        t.back();

        //System.out.println(result.value);

    }
}
