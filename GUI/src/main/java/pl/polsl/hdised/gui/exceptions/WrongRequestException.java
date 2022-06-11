package pl.polsl.hdised.gui.exceptions;

public class WrongRequestException extends Exception {

    public WrongRequestException() {
        super();
    }

    public WrongRequestException(String message) {
        super(message);
    }
}
