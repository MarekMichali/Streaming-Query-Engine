package pl.polsl.hdised.gui.controllers;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.polsl.hdised.gui.temperatureresponse.TemperatureResponseDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OperationController {

    private final String URL = "http://localhost:8081/api/v1/query";

    private ArrayList<String> devices;
    private ArrayList<String> locations;

    public void getAllDevicesAndAllLocationsFromDatabase() {
        devices = getAllDevicesFromDatabase();
        locations = getAllLocationsFromDatabase();
    }

    private ArrayList<String> getAllLocationsFromDatabase(){
        return getStringArrayListFromDatabase("/locations", "location");
    }

    private ArrayList<String> getAllDevicesFromDatabase() {
        return getStringArrayListFromDatabase("/devices", "deviceId");
    }

    private ArrayList<String> getStringArrayListFromDatabase(String urlEnding, String parameter) {
        ArrayList<String> strings = new ArrayList<>();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(URL + urlEnding);
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                JSONArray jsonArray = new JSONArray(EntityUtils.toString(httpResponse.getEntity()));
                for (Object o : jsonArray) {
                    strings.add((String) ((JSONObject) o).get(parameter));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
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

    public double getMinimalTemperatureFromDatabase(String deviceId, String location, String startDate, String finishDate) {
        return getValueFromDatabase("/minimum-temperature", deviceId, location, startDate, finishDate);
    }

    public double getMaximalTemperatureFromDatabase(String deviceId, String location, String startDate, String finishDate) {
        return getValueFromDatabase("/maximum-temperature", deviceId, location, startDate, finishDate);
    }

    private double getValueFromDatabase(String urlEnding, String deviceId, String location, String startDate, String finishDate) {
        double returnValue = 0;

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            StringBuilder stringBuilder = new StringBuilder(URL + urlEnding);
            stringBuilder.append("?deviceId=").append(deviceId);
            stringBuilder.append("&location=").append(location);
            stringBuilder.append("&startDate=").append(startDate);
            stringBuilder.append("&finishDate=").append(finishDate);

            HttpGet request = new HttpGet(stringBuilder.toString());


            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                returnValue = Double.parseDouble(EntityUtils.toString(httpResponse.getEntity()));
                System.out.println(returnValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public ArrayList<TemperatureResponseDto> getAllTemperaturesFromDatabase(String deviceId, String location, String startDate, String finishDate) {
        ArrayList<TemperatureResponseDto> temperatureResponseDtos = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder(URL + "/temperatures");
        stringBuilder.append("?deviceId=").append(deviceId);
        stringBuilder.append("&location=").append(location);
        stringBuilder.append("&startDate=").append(startDate);
        stringBuilder.append("&finishDate=").append(finishDate);

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(stringBuilder.toString());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                JSONArray temperatures = new JSONArray(EntityUtils.toString(httpResponse.getEntity()));
                for (Object temperature : temperatures) {
                    TemperatureResponseDto temperatureResponseDto = new TemperatureResponseDto();
                    var value = ((BigDecimal) ((JSONObject) temperature).get("temperature")).doubleValue();
                    temperatureResponseDto.setTemperature(value);

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.ENGLISH);
                    Date dateTime = format.parse(((String) ((JSONObject) temperature).get("measureDate")).replace('T', ' '));
                    LocalDateTime localDateTime = Instant.ofEpochMilli(dateTime.getTime())
                            .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime();
                    temperatureResponseDto.setMeasureDate(localDateTime);
                    System.out.println(temperatureResponseDto);
                    temperatureResponseDtos.add(temperatureResponseDto);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return temperatureResponseDtos;
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

    public ArrayList<String> getDevices() {
        return devices;
    }

    public ArrayList<String> getLocations() {
        return locations;
    }

    public boolean isConnectionGood() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(URL + "/devices");
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                return true;
            }catch(HttpHostConnectException e){
                System.out.println(e.getMessage());
                return false;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
