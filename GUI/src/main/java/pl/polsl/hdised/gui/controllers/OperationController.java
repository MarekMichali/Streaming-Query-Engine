package pl.polsl.hdised.gui.controllers;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.polsl.hdised.gui.DTOs.MultipleValueDatabaseResponseDTO;
import pl.polsl.hdised.gui.DTOs.TemperatureResponseDTO;

import java.io.IOException;
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
        return getStringArrayListFromDatabase("/historical/locations", "location");
    }

    private ArrayList<String> getAllDevicesFromDatabase() {
        return getStringArrayListFromDatabase("/historical/devices", "deviceId");
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

    public Double getAverageTemperatureFromDatabase(String deviceId, String location, String startDate, String finishDate) {
        return getDoubleFromDatabase("/historical/average", deviceId, location, startDate, finishDate);
    }

    public Double getMinimalTemperatureFromDatabase(String deviceId, String location, String startDate, String finishDate) {
        return getDoubleFromDatabase("/historical/minimum-temperature", deviceId, location, startDate, finishDate);
    }

    public Double getMaximalTemperatureFromDatabase(String deviceId, String location, String startDate, String finishDate) {
        return getDoubleFromDatabase("/historical/maximum-temperature", deviceId, location, startDate, finishDate);
    }

    private Double getDoubleFromDatabase(String urlEnding, String deviceId, String location, String startDate, String finishDate) {
        Double returnValue = 0d;

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            StringBuilder stringBuilder = new StringBuilder(URL + urlEnding);
            stringBuilder.append("?deviceId=").append(deviceId);
            stringBuilder.append("&location=").append(location);
            stringBuilder.append("&startDate=").append(startDate);
            if(startDate.indexOf('%')==-1) stringBuilder.append("%2000:00");
            stringBuilder.append("&finishDate=").append(finishDate);
            if(finishDate.indexOf('%')==-1) stringBuilder.append("%2000:00");

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

    public MultipleValueDatabaseResponseDTO getAllTemperaturesFromDatabase(String deviceId, String location, String startDate, String finishDate) {
        ArrayList<TemperatureResponseDTO> temperatureResponseDTOArrayList = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder(URL + "/historical/temperatures");
        stringBuilder.append("?deviceId=").append(deviceId);
        stringBuilder.append("&location=").append(location);
        stringBuilder.append("&startDate=").append(startDate);
        if(startDate.indexOf('%')==-1) stringBuilder.append("%2000:00");
        stringBuilder.append("&finishDate=").append(finishDate);
        if(finishDate.indexOf('%')==-1) stringBuilder.append("%2000:00");

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(stringBuilder.toString());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                JSONArray temperatures = new JSONArray(EntityUtils.toString(httpResponse.getEntity()));
                for (Object temperature : temperatures) {
                    TemperatureResponseDTO temperatureResponseDTO = new TemperatureResponseDTO();
                    String value = ((JSONObject) temperature).get("temperature").toString();
                    temperatureResponseDTO.setTemperature(value);

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.ENGLISH);
                    Date dateTime = format.parse(((String) ((JSONObject) temperature).get("measureDate")).replace('T', ' '));
                    LocalDateTime localDateTime = Instant.ofEpochMilli(dateTime.getTime())
                            .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime();
                    temperatureResponseDTO.setDateAndTime(localDateTime.toString());
                    System.out.println(temperatureResponseDTOArrayList);
                    temperatureResponseDTOArrayList.add(temperatureResponseDTO);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        MultipleValueDatabaseResponseDTO responseDTO = new MultipleValueDatabaseResponseDTO();
        responseDTO.setValues(temperatureResponseDTOArrayList);
        return responseDTO;
    }

    public void setStreamQueryParameters(String deviceId, String location) throws IOException {
        try(CloseableHttpClient httpClient = HttpClientBuilder.create().build()){
            StringBuilder stringBuilder = new StringBuilder(URL + "/stream/query-parameters");
            stringBuilder.append("?deviceId=").append(deviceId);
            stringBuilder.append("&location=").append(location);

            HttpPost request = new HttpPost(stringBuilder.toString());

            try(CloseableHttpResponse httpResponse = httpClient.execute(request)){
                System.out.println("Parameters set successfully!");
            }
        }
    }

    public double getAverageTemperatureFromStream(String deviceId, String location) {
        Double receivedAverage = 0.0;

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            StringBuilder stringBuilder = new StringBuilder(URL + "/stream/average-temperature");
            stringBuilder.append("?deviceId=").append(deviceId);
            stringBuilder.append("&location=").append(location);

            HttpGet request = new HttpGet(stringBuilder.toString());

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                receivedAverage = Double.parseDouble(EntityUtils.toString(httpResponse.getEntity()));
                System.out.println(receivedAverage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receivedAverage;
    }

    public double getMinimalTemperatureFromStream(String deviceId, String location) {
        Double receivedMinimumTemperature = 0.0;

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            StringBuilder stringBuilder = new StringBuilder(URL + "/stream/minimum-temperature");
            stringBuilder.append("?deviceId=").append(deviceId);
            stringBuilder.append("&location=").append(location);

            HttpGet request = new HttpGet(stringBuilder.toString());

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                receivedMinimumTemperature = Double.parseDouble(EntityUtils.toString(httpResponse.getEntity()));
                System.out.println(receivedMinimumTemperature);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receivedMinimumTemperature;
    }

    public double getMaximalTemperatureFromStream(String deviceId, String location) {
        Double receivedMinimumTemperature = 0.0;

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            StringBuilder stringBuilder = new StringBuilder(URL + "/stream/maximum-temperature");
            stringBuilder.append("?deviceId=").append(deviceId);
            stringBuilder.append("&location=").append(location);

            HttpGet request = new HttpGet(stringBuilder.toString());

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                receivedMinimumTemperature = Double.parseDouble(EntityUtils.toString(httpResponse.getEntity()));
                System.out.println(receivedMinimumTemperature);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receivedMinimumTemperature;
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
