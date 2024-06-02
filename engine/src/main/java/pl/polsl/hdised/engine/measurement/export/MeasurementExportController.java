package pl.polsl.hdised.engine.measurement.export;

import static org.springframework.http.ResponseEntity.ok;

import java.io.IOException;
import java.text.ParseException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<Resource> exportMeasurements(
      @RequestParam("extension") FileExtension fileExtension,
      @RequestParam("deviceId") String deviceId,
      @RequestParam("location") String location,
      @RequestParam("startDate") String stringStartDate,
      @RequestParam("finishDate") String stringFinishDate)
      throws ParseException, IOException {
    HttpHeaders headers = new HttpHeaders();
    headers.add(
        HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileExtension.fileName + "\"");
    headers.add(HttpHeaders.CONTENT_TYPE, fileExtension.contentType);
    return ok().headers(headers)
        .body(service.export(fileExtension, deviceId, location, stringStartDate, stringFinishDate));
  }
}
