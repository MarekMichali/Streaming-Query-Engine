package pl.polsl.hdised.consumer.dto;

public class TemperatureDto {

    private Float temperature;
    private String unit;

    public Float getTemperature() {
        return temperature;
    }

    public String getUnit() {
        return unit;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
