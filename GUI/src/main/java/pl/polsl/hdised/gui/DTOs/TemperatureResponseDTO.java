package pl.polsl.hdised.gui.DTOs;

import java.time.LocalDateTime;

public class TemperatureResponseDTO {

    private String dateAndTime;
    private String device;
    private String location;
    private String temperature;

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        StringBuilder stringBuilder = new StringBuilder(dateAndTime.substring(0,10));
        stringBuilder.append(" ");
        stringBuilder.append(dateAndTime.substring(11,19));
        stringBuilder.append(" ");
        stringBuilder.append(dateAndTime.substring(20));
        stringBuilder.append("ms");
        this.dateAndTime = stringBuilder.toString();
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
