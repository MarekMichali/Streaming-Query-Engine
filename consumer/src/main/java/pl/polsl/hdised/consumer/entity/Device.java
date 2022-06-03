package pl.polsl.hdised.consumer.entity;

import javax.persistence.*;

@Entity(name = "Device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false, columnDefinition = "TEXT")
    private String deviceId;

    public Device() {

    }

    public Device(String deviceId) {
        this.deviceId = deviceId;
    }
}
