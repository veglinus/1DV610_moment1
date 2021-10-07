package Tokenizer;

import java.util.ArrayList;

public class Grammar {
    ArrayList<TokenType> rules = new ArrayList<TokenType>();

    public void add(TokenType token) {
        this.rules.add(token);
    }

    public ArrayList<Token> findRegexMatches(String input) {
        ArrayList<Token> matches = new ArrayList<Token>();

        for (int i = 0; i < this.rules.size(); i++) { // Check string against every rule
            TokenType current = this.rules.get(i);

            Token match = current.matchRegex(input);

            if (match != null) {
                matches.add(match); 
            }
        }

        return matches;
    }
}
