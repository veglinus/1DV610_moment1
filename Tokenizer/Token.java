package Tokenizer;

public class Token {
    public String type = "";
    public String value = "";

    Token(String typeName, String value) {
        this.type = typeName;
        this.value = value;
    }

    public String toString() {
        return this.type + "(" + this.value + ")";
    }
}