package pl.polsl.hdised.gui.temperatureresponse;

import java.time.LocalDateTime;

public class TemperatureResponseDto {

    private LocalDateTime measureDate;
    private Double temperature;

    public void setMeasureDate(LocalDateTime measureDate) {
        this.measureDate = measureDate;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
