package pl.polsl.hdised.gui.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

public class OperationController {

    private ArrayList<String> allDevices;
    private ArrayList<String> allLocations;

    OperationController(){
        allDevices = getAllDevicesFromDatabase();
        allLocations = getAllLocationsFromDatabase();
    }

    private ArrayList<String> getAllLocationsFromDatabase() {
        return null;
    }
    private ArrayList<String> getAllDevicesFromDatabase() {
        return null;
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

    public ArrayList<String> getAllDevices() {
        return allDevices;
    }
    public ArrayList<String> getAllLocations() {
        return allLocations;
    }
}
