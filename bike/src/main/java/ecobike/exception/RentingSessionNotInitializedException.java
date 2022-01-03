package ecobike.exception;

public class RentingSessionNotInitializedException extends RuntimeException{
    public RentingSessionNotInitializedException() {
        super("No renting session initialized yet");
    }

    public RentingSessionNotInitializedException(String msg) {
        super(msg);
    }
}
