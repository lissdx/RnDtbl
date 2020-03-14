package org.yajc.parser;

// Token node is
public class Token {
    private final TokenType type;
    private final String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;

        if (!(o instanceof Token)) {
            return false;
        }

        Token other = (Token) o;

        return other.type.equals(type) &&
                other.value.equals(value);
    }
}
