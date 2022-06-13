package pl.polsl.hdised.gui.services;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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

public class DatabaseService {

    private static final String URL = "http://localhost:8081/api/v1/query/historical";

    public static String getAverageTemperatureFromDatabase(String deviceId, String location, String startDate, String finishDate) {
        return getValueFromDatabase("/average", deviceId, location, startDate, finishDate);
    }
    public static String getMinimalTemperatureFromDatabase(String deviceId, String location, String startDate, String finishDate) {
        return getValueFromDatabase("/minimum-temperature", deviceId, location, startDate, finishDate);
    }
    public static String getMaximalTemperatureFromDatabase(String deviceId, String location, String startDate, String finishDate) {
        return getValueFromDatabase("/maximum-temperature", deviceId, location, startDate, finishDate);
    }
    private static String getValueFromDatabase(String urlEnding, String deviceId, String location, String startDate, String finishDate) {
        String returnValue = "";

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            String requestURL = buildRequestURL(urlEnding,deviceId,location,startDate,finishDate);

            HttpGet request = new HttpGet(requestURL);

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                returnValue = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    private static String buildRequestURL(String urlEnding, String deviceId, String location, String startDate, String finishDate) {
        StringBuilder stringBuilder = new StringBuilder(URL + urlEnding);
        stringBuilder.append("?deviceId=").append(deviceId);
        stringBuilder.append("&location=").append(location);
        stringBuilder.append("&startDate=").append(startDate);
        if(startDate.indexOf('%')==-1) stringBuilder.append("%2000:00");
        stringBuilder.append("&finishDate=").append(finishDate);
        if(finishDate.indexOf('%')==-1) stringBuilder.append("%2000:00");
        return stringBuilder.toString();
    }

    public static ArrayList<TemperatureResponseDTO> getAllTemperaturesFromDatabase(String deviceId, String location, String startDate, String finishDate) {
        ArrayList<TemperatureResponseDTO> temperatureResponseDTOArrayList = new ArrayList<>();

        String requestURL = buildRequestURL("/temperatures",deviceId,location,startDate,finishDate);

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(requestURL);
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
                    temperatureResponseDTOArrayList.add(temperatureResponseDTO);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return temperatureResponseDTOArrayList;
    }

    public static ArrayList<String> getAllLocationsFromDatabase(){
        return getStringArrayListFromDatabase("/locations", "location");
    }
    public static ArrayList<String> getAllDevicesFromDatabase() {
        return getStringArrayListFromDatabase("/devices", "deviceId");
    }
    private static ArrayList<String> getStringArrayListFromDatabase(String urlEnding, String parameter) {
        ArrayList<String> strings = new ArrayList<>();

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            String requestURL = buildRequestURL(urlEnding);
            HttpGet request = new HttpGet(requestURL);
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

    private static String buildRequestURL(String urlEnding) {
        return URL + urlEnding;
    }

}
