package ecobike.common.exception;

public class InternalServerErrorException extends PaymentException {
    public InternalServerErrorException() {
        super("ERROR: Internal Server Error!");
    }
}
