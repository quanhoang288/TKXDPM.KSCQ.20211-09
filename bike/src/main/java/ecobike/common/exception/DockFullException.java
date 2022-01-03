package ecobike.common.exception;

public class DockFullException extends RuntimeException{
    public DockFullException() {
        super("This dock is currently full!");
    }

    public DockFullException(String msg) {
        super(msg);
    }
}
