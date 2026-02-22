package jetlearnsystem;

public class JetParseException extends Exception {
    private int position;

    public JetParseException(String message, int position) {
        super(message);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public JetParseException(String message, int position, Throwable cause) {
        super(message, cause);
        this.position = position;
    }
}