package pl.polsl.hdised.engine.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.polsl.hdised.engine.location.model.LocationEntity;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

  @Query(
      value =
          "SELECT DISTINCT * \n"
              + "FROM location\n"
              + "WHERE location.city = :city\n"
              + "LIMIT 1\n",
      nativeQuery = true)
  LocationEntity findLocationByCity(@Param("city") String city);
}
