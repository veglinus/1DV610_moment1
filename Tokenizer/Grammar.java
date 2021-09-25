package Tokenizer;

import java.util.ArrayList;

public class Grammar {
    ArrayList<TokenType> rules = new ArrayList<TokenType>();

    public void add(TokenType token) {
        this.rules.add(token);
    }
}
