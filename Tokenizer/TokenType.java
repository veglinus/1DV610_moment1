package Tokenizer;

public class TokenType {
    String type;
    String regex;

    public TokenType(String typeName, String regex) {
        this.type = typeName;
        this.regex = regex;
    }
}
