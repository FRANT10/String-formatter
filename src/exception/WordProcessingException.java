package exception;

public class WordProcessingException extends RuntimeException {
    public WordProcessingException() {
        super();
    }

    public WordProcessingException(String message) {
        super(message);
    }

    public WordProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordProcessingException(Throwable cause) {
        super(cause);
    }

    protected WordProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
