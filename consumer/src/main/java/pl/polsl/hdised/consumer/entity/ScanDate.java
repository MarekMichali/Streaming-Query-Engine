package pl.polsl.hdised.consumer.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Date")
public class ScanDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scan_date", nullable = false, columnDefinition = "DATE")
    private Date scanDate;

    public ScanDate() {
    }

    public ScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }
}
