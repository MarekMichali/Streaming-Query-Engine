package pl.polsl.hdised.consumer.date;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.hdised.consumer.date.DateEntity;

public interface DateRepository extends JpaRepository<DateEntity, Long> {
}
