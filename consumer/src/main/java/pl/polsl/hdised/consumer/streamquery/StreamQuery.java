package pl.polsl.hdised.consumer.streamquery;

import pl.polsl.hdised.consumer.exception.EmptyMeasurementsException;
import pl.polsl.hdised.consumer.limitedqueue.LimitedQueue;
import pl.polsl.hdised.consumer.measurement.MeasurementDto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class StreamQuery {

    private static volatile StreamQuery instance;

    private String location;
    private String deviceId;
    private LimitedQueue measurementDtos;

    private StreamQuery() {
        this.location = "";
        this.deviceId = "";
        this.measurementDtos = new LimitedQueue();
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

    public String getLocation() {
        return location;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setParameters(String location, String deviceId) {
        this.location = location;
        this.deviceId = deviceId;
    }

    public void appendMeasurement(MeasurementDto measurementDto) {
        this.measurementDtos.add(measurementDto);
    }

    public List<Object> getMeasurements() {
        return Arrays.asList(this.measurementDtos.toArray());
    }

    public Float getMinimumTemperature() throws EmptyMeasurementsException {
        if (this.measurementDtos.getMinimumTemperature().equals(Float.MAX_VALUE)) {
            throw new EmptyMeasurementsException();
        }
        return this.measurementDtos.getMinimumTemperature();
    }

    public Float getMaximumTemperature() throws EmptyMeasurementsException {
        if (this.measurementDtos.getMaximumTemperature().equals(Float.MIN_VALUE)) {
            throw new EmptyMeasurementsException();
        }
        return this.measurementDtos.getMaximumTemperature();
    }

    public Float getAverageTemperature() throws EmptyMeasurementsException {
        if (this.measurementDtos.isEmpty()) {
            throw new EmptyMeasurementsException();
        }
        return this.measurementDtos.getAverageTemperature();
    }

}
