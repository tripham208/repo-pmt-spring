package vn.id.pmt.spring.exception;


import jakarta.annotation.Nonnull;

public class AlreadyExistsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Already Exists!";

    public AlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public AlreadyExistsException(@Nonnull final String message) {
        super(message);
    }

    public AlreadyExistsException(@Nonnull final String message, @Nonnull final Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(@Nonnull final Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
