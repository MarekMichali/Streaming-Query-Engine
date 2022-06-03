package pl.polsl.hdised.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.hdised.consumer.entity.ScanDate;

public interface ScanDateRepository extends JpaRepository<ScanDate, Long> {
}
