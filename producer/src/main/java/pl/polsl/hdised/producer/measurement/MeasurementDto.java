package pl.polsl.hdised.producer.measurement;

import java.util.Date;

public class MeasurementDto {

    private final String cityName;
    private final Date date;
    private final String deviceId;
    private final Float temperature;
    private final String unit;

    public MeasurementDto(String cityName, Date date, String deviceId, Float temperature, String unit) {
        this.cityName = cityName;
        this.date = date;
        this.deviceId = deviceId;
        this.temperature = temperature;
        this.unit = unit;
    }

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
