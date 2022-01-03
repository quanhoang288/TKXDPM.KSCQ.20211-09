package ecobike.common.exception;

public class BikeAlreadyRentedException extends RuntimeException{
    public BikeAlreadyRentedException() {
        super("This bike is currently being rented. Please select another one");
    }

    public BikeAlreadyRentedException(String msg) {
        super(msg);
    }
}
