package ecobike.common.exception;

public class UserNotRentingException extends RuntimeException{
    public UserNotRentingException() {
        super("You are not currently renting any bike");
    }

    public UserNotRentingException(String msg) {
        super(msg);
    }
}
