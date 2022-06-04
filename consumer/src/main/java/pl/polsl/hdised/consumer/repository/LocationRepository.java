package pl.polsl.hdised.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.polsl.hdised.consumer.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(value = "SELECT DISTINCT * \n" +
            "FROM location\n" +
            "WHERE location.city = :city\n" +
            "LIMIT 1\n", nativeQuery = true)
    Location findLocationByCity(@Param("city") String city);

}
