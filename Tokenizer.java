import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Rename things well

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

    public void next() {
        resolve();
    }
    public void back() {

    }


    public Token resolve() {
        System.out.println(input);
        ArrayList<Token> list = new ArrayList<Token>();

        for (int i = 0; i < grammar.rules.size(); i++) { // Check string against every rule
            list.add(
                match(
                    grammar.rules.get(i).type,
                    grammar.rules.get(i).rule,
                    input)
                );
        }

        // Check which token detected is the first one found:

        // TODO: Check which one is the longest
        // TODO: Apply maximal munch


        if (list.size() > 1) { // Our match function found more than 1 match
            List<Integer> indexlist = new ArrayList<Integer>();

            for (Token token : list) { // Add where the tokens are found to a list
                indexlist.add(input.indexOf(token.value));
            }

            int lowest = Collections.min(indexlist); // Find the lowest value, aka first occurance
            int position = indexlist.indexOf(lowest);

            remove(list.get(position).value);
            return list.get(position);

        } else { // If there's only one Regex find, just return it
            remove(list.get(0).value);
            return list.get(0);
        }
        
    }

    public void remove(String remove) {
        var start = input.indexOf(remove);
        input = input.substring(start + remove.length()); // Removes the found value so we don't get duplicates

        System.out.println("Input is now: " + input);
    }

    public Token match(String type, String rule, String input) {
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

    /*
    public Token MaxmimalMunch(String input) {


        return;
    }
    */
}


class Grammar {
    ArrayList<TokenType> rules = new ArrayList<TokenType>();

    void add(TokenType token) { // Adds token to rules
        rules.add(token);
    }
}