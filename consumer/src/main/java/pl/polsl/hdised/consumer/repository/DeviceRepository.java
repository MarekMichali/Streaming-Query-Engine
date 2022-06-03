package pl.polsl.hdised.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.hdised.consumer.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
