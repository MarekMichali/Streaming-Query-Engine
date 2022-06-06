package pl.polsl.hdised.consumer.query;

import java.util.Objects;

public final class Query {

    private static volatile Query instance;

    private String location;
    private String deviceId;
    private Float temperaturesSum;
    private Integer temperaturesCount;

    private Query() {
        this.temperaturesSum = 0.0f;
        this.temperaturesCount = 0;
        this.location = "";
        this.deviceId = "";
    }

    public static Query getInstance() {
        Query result = instance;
        if (!Objects.isNull(result)) {
            return result;
        }

        synchronized (Query.class) {
            if (Objects.isNull(instance)) {
                instance = new Query();
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

    public void setParameters(String location, String deviceId) {
        this.temperaturesSum = 0.0f;
        this.temperaturesCount = 0;
        this.location = location;
        this.deviceId = deviceId;
    }

    public void appendMeasurement(Float temperature) {
        this.temperaturesSum += temperature;
        this.temperaturesCount += 1;
    }


}
