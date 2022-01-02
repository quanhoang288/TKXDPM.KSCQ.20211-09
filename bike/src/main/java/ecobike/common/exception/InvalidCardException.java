package ecobike.common.exception;

public class InvalidCardException extends PaymentException {
    public InvalidCardException() {
        super("ERROR: Invalid card!");
    }

    public InvalidCardException(String msg) {
        super(msg);
    }
}
