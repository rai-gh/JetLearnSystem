package jetlearnsystem;

public class JetLexerException extends Exception {
    private int position;

    public JetLexerException(String message, int position) {
        super(message);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}