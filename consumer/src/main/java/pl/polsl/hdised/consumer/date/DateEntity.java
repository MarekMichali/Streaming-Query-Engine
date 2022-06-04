package pl.polsl.hdised.consumer.date;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Date")
public class DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scan_date", nullable = false, columnDefinition = "DATE")
    private Date scanDate;

    public DateEntity() {
    }

    public DateEntity(Date scanDate) {
        this.scanDate = scanDate;
    }
}
