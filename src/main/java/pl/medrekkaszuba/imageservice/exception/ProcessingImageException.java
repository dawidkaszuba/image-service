package pl.medrekkaszuba.imageservice.exception;

public class ProcessingImageException extends RuntimeException {

    public ProcessingImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
