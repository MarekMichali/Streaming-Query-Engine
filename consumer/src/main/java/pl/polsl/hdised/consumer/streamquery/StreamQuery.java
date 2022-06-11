package pl.polsl.hdised.consumer.streamquery;

import java.util.Objects;

public final class StreamQuery {

    private static volatile StreamQuery instance;

    private String location;
    private String deviceId;
    private Float temperaturesSum;
    private Integer temperaturesCount;
    private Float minimumTemperature;
    private Float maximumTemperature;

    private StreamQuery() {
        this.temperaturesSum = 0.0f;
        this.temperaturesCount = 0;
        this.location = "";
        this.deviceId = "";
        this.maximumTemperature = Float.MIN_VALUE;
        this.minimumTemperature = Float.MAX_VALUE;
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

    public void appendMeasurement(Float temperature) {
        this.temperaturesSum += temperature;
        this.temperaturesCount += 1;
        if (temperature > this.maximumTemperature) {
            this.maximumTemperature = temperature;
        }
        if (temperature < this.minimumTemperature) {
            this.minimumTemperature = temperature;
        }
    }


}
