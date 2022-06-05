package pl.polsl.hdised.consumer.query;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public final class Query {

    private static volatile Query instance;

    private String location;
    private String deviceId;
    private Date date;
    private Float temperaturesSum;
    private Integer temperaturesCount;

    private Query() {
        this.date = Calendar.getInstance().getTime();
        this.location = "";
        this.deviceId = "";
        this.temperaturesCount = 0;
        this.temperaturesSum = 0.0f;
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

    public Date getDate() {
        return date;
    }

    public void setParameters(String location, String deviceId, Date date) {
        this.temperaturesSum = 0.0f;
        this.temperaturesCount = 0;
        this.location = location;
        this.deviceId = deviceId;
        this.date = date;
    }

    public void appendMeasurement(Float temperature) {
        this.temperaturesSum += temperature;
        this.temperaturesCount += 1;
    }


}
