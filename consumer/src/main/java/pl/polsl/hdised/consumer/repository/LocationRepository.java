package pl.polsl.hdised.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.hdised.consumer.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
