import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class TokenType {
    String type;
    String rule;

    TokenType(String typeName, String rule) {
        this.type = typeName;
        this.rule = rule;
    }

}

class Token {
    String type;
    String value;

    Token(String typeName, String value) {
        this.type = typeName;
        this.value = value;
    }

}

class Tokenizer {

    Grammar grammar;
    String input;

    Tokenizer(Grammar grammar, String input) {
        this.grammar = grammar;
        this.input = input;
    }


    public ArrayList<Token> resolve() {
        ArrayList<Token> list = new ArrayList<Token>();

        for (int i = 0; i < grammar.rules.size(); i++) {
            var rule = grammar.rules.get(i).rule;
            var type = grammar.rules.get(i).type;

            list.add(match(type, rule));
        }


        // TODO: Check which token detected is the longest (also if it's first)

        return list;
    }

    public Token match(String type, String rule) {
        try {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(input);
            boolean matches = matcher.find();

            if (matches) {
                String output = matcher.group(0);
                System.out.println("MATCH");

                return new Token(type, output);
                //list.add(new Token(type, output));
            } else {
                System.out.println("No match");
                return null;
            }

        } catch (Exception e) {
            throw e;
            //TODO: handle exception
        }
    }
}


class Grammar {
    ArrayList<TokenType> rules = new ArrayList<TokenType>();

    void add(TokenType token) { // Adds token to rules
        rules.add(token);
        /*
        rules = Arrays.copyOf(rules, rules.length + 1);
        rules[rules.length -1] = token;
        */
        //System.out.println("Added rule: " + token.rule + " " + token.type);
    }
}