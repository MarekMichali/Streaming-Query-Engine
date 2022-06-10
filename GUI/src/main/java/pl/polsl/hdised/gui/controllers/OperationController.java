package pl.polsl.hdised.gui.controllers;

import javafx.util.converter.LocalDateTimeStringConverter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OperationController {

    private final String URL = "http://localhost:8081/api/v1/query";

    private ArrayList<String> allDevices;
    private ArrayList<String> allLocations;

    OperationController(){
        allDevices = getAllDevicesFromDatabase();
        allLocations = getAllLocationsFromDatabase();
    }

    private ArrayList<String> getAllLocationsFromDatabase(){
        ArrayList<String> stringLocations = new ArrayList<>();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(URL + "/locations");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                JSONArray locations = new JSONArray(EntityUtils.toString(httpResponse.getEntity()));
                for (Object location : locations) {
                    stringLocations.add((String) ((JSONObject) location).get("location"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public double getAverageTemperatureFromDatabase(String deviceId, String location, String startDate, String finishDate) {
        //URL + ?deviceId=dev02&location=Warszawa&startDate=2022-06-06 12:30&finishDate=2022-06-06 18:37
        BigDecimal receivedAverage = new BigDecimal(0);

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            StringBuilder stringBuilder = new StringBuilder(URL);
            stringBuilder.append("?deviceId=").append(deviceId);
            stringBuilder.append("&location=").append(location);
            stringBuilder.append("&startDate=").append(startDate);
            stringBuilder.append("&finishDate=").append(finishDate);

            HttpGet request = new HttpGet(stringBuilder.toString());


            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                JSONObject average = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
                System.out.println(average);
                receivedAverage =  (BigDecimal) average.get("averageTemperature");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return receivedAverage.doubleValue();
    }

    public double getMinimalTemperatureFromDatabase(String device, String location, String startDate, String finishDate) {
        return 0;
    }

    public double getMaximalTemperatureFromDatabase(String device, String location, String startDate, String finishDate) {
        return 0;
    }

    public ArrayList<Double> getAllTemperaturesFromDatabase(String device, String location, String startDate, String finishDate) {
        return new ArrayList<Double>();
    }

    public double getAverageTemperatureFromStream(String device, String location) {
        return 0;
    }

    public double getMinimalTemperatureFromStream(String device, String location) {
        return 0;
    }

    public double getMaximalTemperatureFromStream(String device, String location) {
        return 0;
    }

    public ArrayList<Double> getAllTemperaturesFromStream(String device, String location) {
        return new ArrayList<>();
    }

    public ArrayList<String> getAllDevices() {
        return allDevices;
    }

    public ArrayList<String> getAllLocations() {
        return allLocations;
    }
}
