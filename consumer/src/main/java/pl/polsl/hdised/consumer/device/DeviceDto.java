package pl.polsl.hdised.consumer.device;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;


public class DeviceDto implements Serializable{

    private final String deviceId;

    public DeviceDto(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
