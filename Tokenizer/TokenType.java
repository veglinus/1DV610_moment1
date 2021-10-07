package Tokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenType {
    String type;
    String regex;

    public TokenType(String typeName, String regex) {
        this.type = typeName;
        this.regex = regex;
    }

    public Token matchRegex(String input) {
        try {
            Pattern pattern = Pattern.compile(this.regex);
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
