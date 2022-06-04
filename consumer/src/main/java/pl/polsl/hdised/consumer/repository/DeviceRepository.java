package pl.polsl.hdised.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.polsl.hdised.consumer.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query(value = "SELECT DISTINCT * \n" +
            "FROM device\n" +
            "WHERE device.device_id = :deviceId\n" +
            "LIMIT 1\n", nativeQuery = true)
    Device findDeviceById(@Param("deviceId") String deviceId);

}
