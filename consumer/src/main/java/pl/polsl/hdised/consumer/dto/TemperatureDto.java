package pl.polsl.hdised.consumer.dto;

import java.util.Date;

public class TemperatureDto {

    private String cityName;
    private Date date;
    private String deviceId;
    private Float temperature;
    private String unit;

    public Float getTemperature() {
        return temperature;
    }

    public String getUnit() {
        return unit;
    }

    public String getCityName() {
        return cityName;
    }

    public Date getDate() {
        return date;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
