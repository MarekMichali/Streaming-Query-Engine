package pl.polsl.hdised.consumer.averageresponse;

public class AverageResponseDto {

    private Float averageTemperature;
    private Integer temperaturesCount;

    public AverageResponseDto(Float averageTemperature, Integer temperaturesCount) {
        this.averageTemperature = averageTemperature;
        this.temperaturesCount = temperaturesCount;
    }

    public Float getAverageTemperature() {
        return averageTemperature;
    }

    public Integer getTemperaturesCount() {
        return temperaturesCount;
    }
}
