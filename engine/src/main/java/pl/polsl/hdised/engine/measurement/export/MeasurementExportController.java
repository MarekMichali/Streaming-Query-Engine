package pl.polsl.hdised.engine.measurement.export;

import java.text.ParseException;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.hdised.engine.measurement.export.model.FileExtension;

@RequestMapping("api/v1/measurements/export")
@RestController
public class MeasurementExportController {
  private final MeasurementExportService service;

  public MeasurementExportController(MeasurementExportService service) {
    this.service = service;
  }

  @GetMapping
  public Resource exportMeasurements(
      @RequestParam("extension") FileExtension fileExtension,
      @RequestParam("deviceId") String deviceId,
      @RequestParam("location") String location,
      @RequestParam("startDate") String stringStartDate,
      @RequestParam("finishDate") String stringFinishDate)
      throws ParseException {
    return service.export(fileExtension, deviceId, location, stringStartDate, stringFinishDate);
  }
}
