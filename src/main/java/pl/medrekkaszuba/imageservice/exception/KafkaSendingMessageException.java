package pl.medrekkaszuba.imageservice.exception;

public class KafkaSendingMessageException extends RuntimeException {
    public KafkaSendingMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
