package Tokenizer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grammar {
    ArrayList<TokenType> rules = new ArrayList<TokenType>();

    public void add(TokenType token) {
        this.rules.add(token);
    }

    public ArrayList<Token> findRegexMatches(String input) {
        ArrayList<Token> matches = new ArrayList<Token>();

        for (int i = 0; i < this.rules.size(); i++) { // Check string against every rule
            Token match = matchRegex(
                this.rules.get(i).type,
                this.rules.get(i).regex,
                input);

            if (match != null) {
                matches.add(match); 
            }
        }

        return matches;
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
}
