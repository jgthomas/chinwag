package protocol;

public enum Token {
    SEPARATOR("£££");

    String value;

    Token(String t) {
        value = t;
    }

    public String getValue() {
        return value;
    }
}
