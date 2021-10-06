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
    int activeToken = -1;

    public Tokenizer(Grammar grammar, String input) {
        this.grammar = grammar;
        this.input = input;
        //next(); // To start the application we run next on construction
    }

    public Token next() {
        Boolean endreached = checkForEndofInput();

        if (endreached) {
            handleEndToken();
            return new Token("END", "");
        } else {
            Token t = tokenize();
            //System.out.println(t.type + "(" + t.value + ")");
            activeToken++;
            return (Token) t;
        }

    }

    public void back() {
        
        if (this.activeToken - 1 == -1) {
            throw new IllegalArgumentException("Can't go back further.");
            
        } else {
            // Can cause issues with going backwards
            this.activeToken--;
            Token currentToken = Tokens.get(activeToken);
            System.out.println(currentToken.toString());
        }
        
    }

    private void handleEndToken() {
        if (Tokens.isEmpty()) { // List is empty, just put an end token
            Tokens.add(new Token("END", ""));
            this.activeToken++;
            //System.out.println("END()");
        } else {
            var lastToken = Tokens.get(Tokens.size() - 1);

            if (lastToken.type == "END") {

                if (Tokens.get(activeToken) == lastToken) {
                    System.out.println("Out of bounds.");
                } else {
                    //System.out.println("END()");
                }

            } else {
                Tokens.add(new Token("END", ""));
                this.activeToken++;
                //System.out.println("END()");
            }
        }
    }

    private Boolean checkForEndofInput() {
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
        ArrayList<Token> matches = new ArrayList<Token>();

        for (int i = 0; i < grammar.rules.size(); i++) { // Check string against every rule
                Token match = matchRegex(
                    grammar.rules.get(i).type,
                    grammar.rules.get(i).regex,
                    this.input);

                if (match != null) {
                    matches.add(match); 
                }
        }

        if (!this.input.isEmpty() && matches.isEmpty()) { // Handles lexical errors
            throw new IllegalArgumentException("No lexical element matches input.");
        }

        if (matches.size() > 1) { // Our match function found more than 1 match
            return compareTokens(matches);
        } else { // If there's only one Regex find, just return it
            Tokens.add(new Token(matches.get(0).type, matches.get(0).value));
            removeFromInput(matches.get(0).value);
            return matches.get(0);
        }
    }

    private Token compareTokens(ArrayList<Token> matches) {
        List<Integer> indexlist = new ArrayList<Integer>();
        List<Token> matchingTokens = new ArrayList<Token>();

        for (Token token : matches) { // Add where the tokens are found to a list
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
            Tokens.add(new Token(matches.get(position).type, matches.get(position).value));
            removeFromInput(matches.get(position).value);
            return matches.get(position);
        }
    }

    private boolean checkDuplicateValues(List<Integer> matches) {
        for (Integer number : matches) {
            if (!number.equals(matches.get(0))) { // https://www.baeldung.com/java-list-all-equal
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

    private Token matchRegex(String type, String rule, String input) {
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