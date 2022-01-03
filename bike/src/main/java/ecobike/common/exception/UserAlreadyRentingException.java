package ecobike.common.exception;

public class UserAlreadyRentingException extends RuntimeException{
    public UserAlreadyRentingException() {
        super("You are already renting a bike");
    }

    public UserAlreadyRentingException(String msg) {
        super(msg);
    }
}
