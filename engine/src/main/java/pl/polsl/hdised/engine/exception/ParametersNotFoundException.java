package pl.polsl.hdised.engine.exception;

public class ParametersNotFoundException extends Exception {

    public ParametersNotFoundException() {
        super("Provided parameters are invalid");
    }
}
