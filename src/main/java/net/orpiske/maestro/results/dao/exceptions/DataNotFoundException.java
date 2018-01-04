package net.orpiske.maestro.results.dao.exceptions;

/**
 *
 */
public class DataNotFoundException extends Exception {
    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(Throwable cause) {
        super(cause);
    }
}
