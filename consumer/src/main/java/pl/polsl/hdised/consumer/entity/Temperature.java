package pl.polsl.hdised.consumer.entity;

import javax.persistence.*;

@Entity(name = "Temperature")
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "scan_date_id")
    private ScanDate scanDate;

    @Column(name = "unit", nullable = false, columnDefinition = "TEXT")
    private String unit;
    @Column(name = "temperature", nullable = false)
    private Float temperature;

    public Temperature() {
    }

    public Temperature(Device device, Location location, ScanDate scanDate, String unit, Float temperature) {
        this.device = device;
        this.location = location;
        this.scanDate = scanDate;
        this.unit = unit;
        this.temperature = temperature;
    }
}
