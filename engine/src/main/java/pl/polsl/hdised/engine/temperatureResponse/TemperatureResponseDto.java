package pl.polsl.hdised.engine.temperatureResponse;

import java.time.LocalDateTime;

public class TemperatureResponseDto {

    private final LocalDateTime measureDate;
    private final Float temperature;

    public TemperatureResponseDto(LocalDateTime measureDate, Float temperature) {
        this.measureDate = measureDate;
        this.temperature = temperature;
    }

    public LocalDateTime getMeasureDate() {
        return measureDate;
    }

    public Float getTemperature() {
        return temperature;
    }
}
