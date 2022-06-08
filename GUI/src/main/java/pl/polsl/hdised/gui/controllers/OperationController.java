package pl.polsl.hdised.gui.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

public class OperationController {

    OperationController(){

    }

    public double getAverageTemperatureFromDatabase(String device, String location, LocalDate minDate, LocalDate maxDate) {
        return 0;
    }
    public double getMinimalTemperatureFromDatabase(String device, String location, LocalDate minDate, LocalDate maxDate) {
        return 0;
    }
    public double getMaximalTemperatureFromDatabase(String device, String location, LocalDate minDate, LocalDate maxDate) {
        return 0;
    }
    public ArrayList<Double> getAllTemperaturesFromDatabase(String device, String location, LocalDate minDate, LocalDate maxDate) {
        return new ArrayList<Double>();
    }
    public double getAverageTemperatureFromStream(String device, String location, LocalDate minDate, LocalDate maxDate) {
        return 0;
    }
    public double getMinimalTemperatureFromStream(String device, String location, LocalDate minDate, LocalDate maxDate) {
        return 0;
    }
    public double getMaximalTemperatureFromStream(String device, String location, LocalDate minDate, LocalDate maxDate) {
        return 0;
    }
    public ArrayList<Double> getAllTemperaturesFromStream(String device, String location, LocalDate minDate, LocalDate maxDate) {
        return new ArrayList<Double>();
    }
}
