package pl.polsl.hdised.consumer.measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.Date;

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Long> {

    @Query(value = "SELECT AVG(temperature) as averageTemperature, COUNT(*) as temperaturesCount\n" +
            "FROM measurement \n" +
            "join device on measurement.device_id = device.id\n" +
            "join location on measurement.location_id = location.id\n" +
            "join date on measurement.scan_date_id = date.id\n" +
            "WHERE \n" +
            "device.device_id = :deviceId AND\n" +
            "location.city = :location AND\n" +
            "date.scan_date = :date", nativeQuery = true)
    Tuple getAverage(@Param("deviceId") String deviceId, @Param("location") String location, @Param("date") Date date);
}
