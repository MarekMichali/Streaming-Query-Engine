package pl.polsl.hdised.consumer.date;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DateRepository extends JpaRepository<DateEntity, Long> {
}
