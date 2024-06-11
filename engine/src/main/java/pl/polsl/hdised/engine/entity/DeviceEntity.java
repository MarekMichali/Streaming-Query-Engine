package pl.polsl.hdised.engine.entity;

import javax.persistence.*;

@Entity(name = "Device")
public class DeviceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "device_id", nullable = false, columnDefinition = "TEXT")
  private String deviceId;

  public DeviceEntity() {}

  public DeviceEntity(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getDeviceId() {
    return deviceId;
  }
}
