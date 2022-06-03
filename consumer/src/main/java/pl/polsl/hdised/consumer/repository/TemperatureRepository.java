package pl.polsl.hdised.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.hdised.consumer.entity.Temperature;

public interface TemperatureRepository extends JpaRepository<Temperature, Long> {
}
