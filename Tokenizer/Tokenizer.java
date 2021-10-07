package Tokenizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tokenizer {

    ArrayList<Token> Tokens = new ArrayList<Token>();
    Grammar grammar;
    String input;
    int activeToken = 0;

    public Tokenizer(Grammar grammar, String input) {
        this.grammar = grammar;
        this.input = input;
        setFirstToken();
    }

    public Token getActiveToken() {
        return Tokens.get(activeToken);
    }

    public Token next() {
        Boolean endreached = checkForEndofInput();
        if (endreached) {
            return handleEndToken();
        } else {
            Token t = tokenize();
            activeToken++;
            //System.out.println(Tokens.get(activeToken).toString());
            return (Token) t;
        }
    }

    public Token back() {
        if (this.activeToken - 1 == -1) {
            throw new IllegalArgumentException("Can't go back further.");
        } else {
            // Can cause issues with going backwards
            this.activeToken--;
            //System.out.println((Tokens.get(activeToken).toString());
            return Tokens.get(activeToken);
        }
    }

    private Token tokenize() {
        this.input = this.input.stripLeading();
        ArrayList<Token> matches = grammar.findRegexMatches(this.input);

        if (!this.input.isEmpty() && matches.isEmpty()) { // Handles lexical errors
            throw new IllegalArgumentException("No lexical element matches input.");
        }

        if (matches.size() > 1) { // Our match function found more than 1 match
            return compareTokens(matches);
        } else { // If there's only one Regex find, just return it
            Token token = matches.get(0);
            Tokens.add(new Token(token.type,token.value));
            removeFromInput(token.value);
            return token;
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
            Token firstToken = matches.get(position);
            Tokens.add(firstToken);
            removeFromInput(firstToken.value);
            return firstToken;
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
    
    public Token maxmimalMunch(List<Token> tokens) {
        List<Integer> lengthList = new ArrayList<Integer>();

        for (Token token : tokens) {
            lengthList.add(token.value.length());
        }

        var maxvalue = Collections.max(lengthList); // Longest length = maximal munch winner
        var position = lengthList.indexOf(maxvalue); // Index of winner

        return tokens.get(position); // Return the token at the position of winner
    }

    public Token setFirstToken() {
        Boolean endreached = checkForEndofInput();
        if (endreached) {
            return endToken(); // Sets first token to END, doesn't increase activeToken
        } else {
            Token t = tokenize();
            return (Token) t;
        }
    }
    
    private Token handleEndToken() {
        if (Tokens.get(activeToken).type != "END") {
            activeToken++;
            return endToken();
        } else {
            return getActiveToken();
        }
    }

    private Token endToken() {
        Tokens.add(new Token("END", ""));
        return new Token("END", "");
    }

    private void removeFromInput(String remove) {
        this.input = this.input.strip();
        var start = this.input.indexOf(remove);
        this.input = this.input.substring(start + remove.length()); // Removes the found value so we don't get duplicates
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
}