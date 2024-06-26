package pl.polsl.hdised.engine.device.model;

import java.io.Serializable;

public class DeviceDto implements Serializable {

  private final String deviceId;

  public DeviceDto(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getDeviceId() {
    return deviceId;
  }
}
