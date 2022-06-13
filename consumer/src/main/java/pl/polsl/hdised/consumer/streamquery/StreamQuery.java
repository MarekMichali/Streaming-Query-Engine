package pl.polsl.hdised.consumer.streamquery;

import pl.polsl.hdised.consumer.limitedqueue.LimitedQueue;
import pl.polsl.hdised.consumer.measurement.MeasurementDto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class StreamQuery {

    private static volatile StreamQuery instance;

    private String location;
    private String deviceId;
    private Float temperaturesSum;
    private Integer temperaturesCount;
    private Float minimumTemperature;
    private Float maximumTemperature;
    private LimitedQueue<MeasurementDto> measurementDtos;

    private StreamQuery() {
        this.temperaturesSum = 0.0f;
        this.temperaturesCount = 0;
        this.location = "";
        this.deviceId = "";
        this.maximumTemperature = Float.MIN_VALUE;
        this.minimumTemperature = Float.MAX_VALUE;
        this.measurementDtos = new LimitedQueue<>();
    }

    public static StreamQuery getInstance() {
        StreamQuery result = instance;
        if (!Objects.isNull(result)) {
            return result;
        }

        synchronized (StreamQuery.class) {
            if (Objects.isNull(instance)) {
                instance = new StreamQuery();
            }
            return instance;
        }
    }

    public Float getTemperaturesSum() {
        return temperaturesSum;
    }

    public Integer getTemperaturesCount() {
        return temperaturesCount;
    }

    public String getLocation() {
        return location;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Float getMinimumTemperature() {
        return minimumTemperature;
    }

    public Float getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setParameters(String location, String deviceId) {
        this.temperaturesSum = 0.0f;
        this.temperaturesCount = 0;
        this.location = location;
        this.deviceId = deviceId;
        this.maximumTemperature = Float.MIN_VALUE;
        this.minimumTemperature = Float.MAX_VALUE;
    }

    public void appendMeasurement(MeasurementDto measurementDto) {
        this.temperaturesSum += measurementDto.getTemperature();
        this.temperaturesCount += 1;
        if (measurementDto.getTemperature() > this.maximumTemperature) {
            this.maximumTemperature = measurementDto.getTemperature();
        }
        if (measurementDto.getTemperature() < this.minimumTemperature) {
            this.minimumTemperature = measurementDto.getTemperature();
        }
        this.measurementDtos.add(measurementDto);
    }

    public List<Object> getMeasurements() {
        return Arrays.asList(this.measurementDtos.toArray());
    }

}
