package pl.polsl.hdised.consumer.entity;

import javax.persistence.*;

@Entity(name = "Location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", nullable = false, columnDefinition = "TEXT")
    private String city;

    public Location() {

    }

    public Location(String city) {
        this.city = city;
    }
}
