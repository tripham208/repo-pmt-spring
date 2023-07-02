package vn.id.pmt.spring.exception;


import jakarta.annotation.Nonnull;

public class NotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Not Found.";

    public NotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundException(@Nonnull final String message) {
        super(message);
    }

    public NotFoundException(@Nonnull final String message, @Nonnull final Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(@Nonnull final Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
