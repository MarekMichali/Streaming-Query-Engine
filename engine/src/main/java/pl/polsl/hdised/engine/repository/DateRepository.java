package pl.polsl.hdised.engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.hdised.engine.entity.DateEntity;

public interface DateRepository extends JpaRepository<DateEntity, Long> {}
