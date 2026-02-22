package jetlearnsystem;
public class JetExecutionException extends Exception {
    public JetExecutionException(String message) {
        super(message);
    }

    public JetExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}