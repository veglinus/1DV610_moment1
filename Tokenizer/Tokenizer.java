package Tokenizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    ArrayList<Token> Tokens = new ArrayList<Token>();
    Grammar grammar;
    String input;

    int currentTokenIndex;

    public Tokenizer(Grammar grammar, String input) {
        this.grammar = grammar;
        this.input = input;

        next(); // To start the application we run next on construction
    }

    public void next() {
        Boolean endreached = checkForEnd();

        if (endreached) {
            endOfInput();
        } else {
            Token t = tokenize();
            System.out.println(t.type + "(" + t.value + ")");
            currentTokenIndex++;
        }

    }

    public void back() {
        if (this.currentTokenIndex != 0) {
            this.currentTokenIndex--;
        }

        Token currentToken = Tokens.get(currentTokenIndex);
        System.out.println(currentToken.toString());
    }

    private void endOfInput() {
        if (Tokens.isEmpty()) { // END token doesn't exist, add it.
            Tokens.add(new Token("END", ""));
            System.out.println("END()");
        } else {
            var lastToken = Tokens.get(Tokens.size() - 1);

            if (lastToken.type == "END") {
                System.out.println("End of data.");
            } else {
                // END token doesn't exist, add it.
                Tokens.add(new Token("END", ""));
                System.out.println("END()");
            }
        }
    }

    private Boolean checkForEnd() {
        String trimmedInput = this.input.trim();

        if (trimmedInput == "") {
            return true;
        } else {
            if (trimmedInput != null && !trimmedInput.isEmpty()) {
                return false;
            } else {
                return true;
                
            }

        }
    }


    private Token tokenize() {
        this.input = this.input.stripLeading();
        ArrayList<Token> list = new ArrayList<Token>();

        for (int i = 0; i < grammar.rules.size(); i++) { // Check string against every rule
            
                var add = match(
                    grammar.rules.get(i).type,
                    grammar.rules.get(i).rule,
                    this.input);

                if (add != null) {
                    list.add(add); 
                }
        }

        if (!this.input.isEmpty() && list.isEmpty()) { // Hanterar lexikala fel
            throw new IllegalArgumentException("No lexical element matches input.");
        }


        if (list.size() > 1) { // Our match function found more than 1 match
            List<Integer> indexlist = new ArrayList<Integer>();
            List<Token> matchingTokens = new ArrayList<Token>();

            for (Token token : list) { // Add where the tokens are found to a list
                indexlist.add(this.input.indexOf(token.value));
                matchingTokens.add(token);
            }

            int lowest = Collections.min(indexlist); // Find the lowest value, aka first occurance
            int position = indexlist.indexOf(lowest);

            if (checkDuplicateValues(indexlist)) { // If two values are the same, we do maximal munch
                Token winner = maxmimalMunch(matchingTokens);
                Tokens.add(winner);
                removeFromInput(winner.value);
                return winner;

            } else { // All matches are at different positions, so we grab the first occurance
                Tokens.add(new Token(list.get(position).type, list.get(position).value));
                removeFromInput(list.get(position).value);
                return list.get(position);
            }

        } else { // If there's only one Regex find, just return it

            Tokens.add(new Token(list.get(0).type, list.get(0).value));
            removeFromInput(list.get(0).value);
            return list.get(0);
        }
        
    }

    private boolean checkDuplicateValues(List<Integer> list) {
        for (Integer number : list) {
            if (!number.equals(list.get(0))) { // https://www.baeldung.com/java-list-all-equal
                return false;
            }
        }
        return true;
    }

    private void removeFromInput(String remove) {
        this.input = this.input.strip();
        var start = this.input.indexOf(remove);
        this.input = this.input.substring(start + remove.length()); // Removes the found value so we don't get duplicates
    }

    private Token match(String type, String rule, String input) {
        try {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(input);
            boolean matches = matcher.find();

            if (matches) {
                String output = matcher.group(0);
                return new Token(type, output);
            } else {
                return null;
            }

        } catch (Exception e) {
            throw e;
        }
    }

    
    public Token maxmimalMunch(List<Token> tokens) {
        List<Integer> lengthList = new ArrayList<Integer>();

        for (Token token : tokens) {
            lengthList.add(token.value.length());
        }

        var maxvalue = Collections.max(lengthList); // Longest length = maximal munch winner
        var position = lengthList.indexOf(maxvalue); // Index of winner

        return tokens.get(position); // Return the token at the position of winner
    }
    
}