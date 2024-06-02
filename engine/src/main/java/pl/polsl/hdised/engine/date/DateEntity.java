package pl.polsl.hdised.engine.date;

import java.util.Date;
import javax.persistence.*;

@Entity(name = "Date")
public class DateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "scan_date", nullable = false, columnDefinition = "TIMESTAMP")
  private Date scanDate;

  public DateEntity() {}

  public DateEntity(Date scanDate) {
    this.scanDate = scanDate;
  }

  public Date getScanDate() {
    return scanDate;
  }
}
