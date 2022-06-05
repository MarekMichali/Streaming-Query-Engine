package pl.polsl.hdised.consumer.exception;

public class ParametersNotFoundException extends Exception {

    public ParametersNotFoundException() {
        super("Provided parameters are invalid");
    }
}
