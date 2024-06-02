package pl.polsl.hdised.engine.measurement;

import java.util.Date;
import java.util.List;
import javax.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.polsl.hdised.engine.measurement.model.MeasurementEntity;

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Long> {

  @Query(
      value =
          "SELECT AVG(temperature) as averageTemperature\n"
              + "FROM measurement \n"
              + "join device on measurement.device_id = device.id\n"
              + "join location on measurement.location_id = location.id\n"
              + "join date on measurement.scan_date_id = date.id\n"
              + "WHERE \n"
              + "device.device_id = :deviceId AND\n"
              + "location.city = :location AND\n"
              + "date.scan_date BETWEEN :startDate AND :finishDate",
      nativeQuery = true)
  Float getAverage(
      @Param("deviceId") String deviceId,
      @Param("location") String location,
      @Param("startDate") Date startDate,
      @Param("finishDate") Date finishDate);

  @Query(
      value =
          "SELECT MIN(temperature) as averageTemperature\n"
              + "FROM measurement \n"
              + "join device on measurement.device_id = device.id\n"
              + "join location on measurement.location_id = location.id\n"
              + "join date on measurement.scan_date_id = date.id\n"
              + "WHERE \n"
              + "device.device_id = :deviceId AND\n"
              + "location.city = :location AND\n"
              + "date.scan_date BETWEEN :startDate AND :finishDate",
      nativeQuery = true)
  Float getMinimumTemperature(
      @Param("deviceId") String deviceId,
      @Param("location") String location,
      @Param("startDate") Date startDate,
      @Param("finishDate") Date finishDate);

  @Query(
      value =
          "SELECT MAX(temperature) as averageTemperature\n"
              + "FROM measurement \n"
              + "join device on measurement.device_id = device.id\n"
              + "join location on measurement.location_id = location.id\n"
              + "join date on measurement.scan_date_id = date.id\n"
              + "WHERE \n"
              + "device.device_id = :deviceId AND\n"
              + "location.city = :location AND\n"
              + "date.scan_date BETWEEN :startDate AND :finishDate",
      nativeQuery = true)
  Float getMaximumTemperature(
      @Param("deviceId") String deviceId,
      @Param("location") String location,
      @Param("startDate") Date startDate,
      @Param("finishDate") Date finishDate);

  @Query(
      value =
          "SELECT temperature as temperature, date.scan_date as date\n"
              + "FROM measurement \n"
              + "join device on measurement.device_id = device.id\n"
              + "join location on measurement.location_id = location.id\n"
              + "join date on measurement.scan_date_id = date.id\n"
              + "WHERE \n"
              + "device.device_id = :deviceId AND\n"
              + "location.city = :location AND\n"
              + "date.scan_date BETWEEN :startDate AND :finishDate",
      nativeQuery = true)
  List<Tuple> getAllTemperatures(
      @Param("deviceId") String deviceId,
      @Param("location") String location,
      @Param("startDate") Date startDate,
      @Param("finishDate") Date finishDate);

  List<MeasurementEntity>
      getAllByDateEntityScanDateBetweenAndDeviceEntityDeviceIdAndLocationEntity_City(
          Date dateEntity_scanDate,
          Date dateEntity_scanDate2,
          String deviceEntity_deviceId,
          String locationEntity_city);
}
