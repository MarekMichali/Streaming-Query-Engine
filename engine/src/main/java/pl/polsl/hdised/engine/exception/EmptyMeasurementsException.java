package pl.polsl.hdised.engine.exception;

public class EmptyMeasurementsException extends Exception {

  public EmptyMeasurementsException() {
    super("Measurements from device and location with provided parameters has not been found yet");
  }
}
