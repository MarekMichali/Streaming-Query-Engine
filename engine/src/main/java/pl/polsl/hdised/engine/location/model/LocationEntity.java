package pl.polsl.hdised.engine.location.model;

import javax.persistence.*;

@Entity(name = "Location")
public class LocationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "city", nullable = false, columnDefinition = "TEXT")
  private String city;

  public LocationEntity() {}

  public LocationEntity(String city) {
    this.city = city;
  }

  public String getCity() {
    return city;
  }
}
