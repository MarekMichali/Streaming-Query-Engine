package pl.polsl.hdised.producer.dto;

public class TemperatureDto {

    private Float temperature;
    private String unit;

    public TemperatureDto(Float temperature, String unit) {
        this.temperature = temperature;
        this.unit = unit;
    }

    public Float getTemperature() {
        return temperature;
    }

    public String getUnit() {
        return unit;
    }

}
