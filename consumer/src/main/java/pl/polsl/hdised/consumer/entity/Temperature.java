package pl.polsl.hdised.consumer.entity;

import javax.persistence.*;

@Entity(name = "Temperature")
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "unit", nullable = false, columnDefinition = "TEXT")
    private String unit;
    @Column(name = "temperature", nullable = false)
    private Float temperature;

    public Temperature() {
    }

    public Temperature(String unit, Float temperature) {
        this.unit = unit;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }
}
