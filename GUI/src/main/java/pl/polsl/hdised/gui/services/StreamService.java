package pl.polsl.hdised.gui.services;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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

public class StreamService {
    private static final String URL = "http://localhost:8081/api/v1/query/stream";

    public static boolean setStreamQueryParameters(String deviceId, String location) throws IOException {
        try(CloseableHttpClient httpClient = HttpClientBuilder.create().build()){
            String requestURL = buildRequestURL("/query-parameters",deviceId,location);
            HttpPost request = new HttpPost(requestURL);
            try(CloseableHttpResponse httpResponse = httpClient.execute(request)){
                return true;
            }catch(Exception any){
                return false;
            }
        }
    }

    private static String buildRequestURL(String urlEnding, String deviceId, String location) {
        return buildRequestURL(urlEnding) + "?deviceId=" + deviceId +
                "&location=" + location;
    }
    private static String buildRequestURL(String urlEnding) {
        return URL + urlEnding;
    }

    public static String getAverageTemperatureFromStream() {
        return getValueFromStream("/average-temperature");
    }
    public static String getMinimalTemperatureFromStream() {

        return getValueFromStream("/minimum-temperature");
    }
    public static String getMaximalTemperatureFromStream() {
        return getValueFromStream("/maximum-temperature");
    }
    public static String getValueFromStream(String urlEnding) {
        String value = "";

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            String requestURL = buildRequestURL(urlEnding);

            HttpGet request = new HttpGet(requestURL);

            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                value = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static ArrayList<TemperatureResponseDTO> getAllTemperaturesFromStream() {
        ArrayList<TemperatureResponseDTO> temperatureResponseDTOArrayList = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder(URL + "/temperatures");

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(stringBuilder.toString());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                JSONArray temperatures = new JSONArray(EntityUtils.toString(httpResponse.getEntity()));
                for (Object temperature : temperatures) {
                    TemperatureResponseDTO temperatureResponseDTO = new TemperatureResponseDTO();

                    String value = ((JSONObject) temperature).get("temperature").toString();
                    temperatureResponseDTO.setTemperature(value);

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.ENGLISH);
                    Date dateTime = format.parse(((String) ((JSONObject) temperature).get("date")).replace('T', ' '));
                    LocalDateTime localDateTime = Instant.ofEpochMilli(dateTime.getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    temperatureResponseDTO.setDateAndTime(localDateTime.toString());

                    String deviceId = ((JSONObject) temperature).get("deviceId").toString();
                    temperatureResponseDTO.setDevice(deviceId);

                    String location = ((JSONObject) temperature).get("cityName").toString();
                    temperatureResponseDTO.setLocation(location);

                    temperatureResponseDTOArrayList.add(temperatureResponseDTO);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return temperatureResponseDTOArrayList;
    }
}
