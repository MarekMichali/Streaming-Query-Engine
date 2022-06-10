package pl.polsl.hdised.gui.controllers;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OperationController {

    private final String URL = "http://localhost:8081/api/v1/query";

    private ArrayList<String> allDevices;
    private ArrayList<String> allLocations;

    OperationController() throws IOException {
        allDevices = getAllDevicesFromDatabase();
        allLocations = getAllLocationsFromDatabase();
    }

    private ArrayList<String> getAllLocationsFromDatabase() throws IOException {
        ArrayList<String> stringLocations = new ArrayList<>();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(URL + "/locations");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                JSONArray locations = new JSONArray(EntityUtils.toString(httpResponse.getEntity()));
                for (Object location : locations) {
                    stringLocations.add((String) ((JSONObject) location).get("location"));
                }
            }
        }
        return stringLocations;
    }

    private ArrayList<String> getAllDevicesFromDatabase() {
        ArrayList<String> stringDevices = new ArrayList<>();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(URL + "/devices");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                JSONArray devices = new JSONArray(EntityUtils.toString(httpResponse.getEntity()));
                for (Object device : devices) {
                    stringDevices.add((String) ((JSONObject) device).get("deviceId"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringDevices;
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
