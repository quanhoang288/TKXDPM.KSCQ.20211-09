package ecobike.common.exception;

public class InvalidCredentialException extends Exception{
    public InvalidCredentialException() {
        super("Bad credential");
    }
}
