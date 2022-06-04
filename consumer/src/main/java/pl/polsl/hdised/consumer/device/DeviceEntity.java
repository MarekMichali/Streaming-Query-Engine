package pl.polsl.hdised.consumer.device;

import javax.persistence.*;

@Entity(name = "Device")
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false, columnDefinition = "TEXT")
    private String deviceId;

    public DeviceEntity() {

    }

    public DeviceEntity(String deviceId) {
        this.deviceId = deviceId;
    }
}
