package pl.polsl.hdised.engine.measurement;

import pl.polsl.hdised.engine.date.DateEntity;
import pl.polsl.hdised.engine.device.DeviceEntity;
import pl.polsl.hdised.engine.location.LocationEntity;

import javax.persistence.*;

@Entity(name = "Measurement")
public class MeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private DeviceEntity deviceEntity;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity locationEntity;

    @ManyToOne
    @JoinColumn(name = "scan_date_id")
    private DateEntity dateEntity;

    @Column(name = "unit", nullable = false, columnDefinition = "TEXT")
    private String unit;
    @Column(name = "temperature", nullable = false)
    private Float temperature;

    public MeasurementEntity() {
    }

    public MeasurementEntity(DeviceEntity deviceEntity, LocationEntity locationEntity, DateEntity dateEntity, String unit, Float temperature) {
        this.deviceEntity = deviceEntity;
        this.locationEntity = locationEntity;
        this.dateEntity = dateEntity;
        this.unit = unit;
        this.temperature = temperature;
    }
}
