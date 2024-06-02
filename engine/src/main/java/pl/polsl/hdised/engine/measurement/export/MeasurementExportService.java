package pl.polsl.hdised.engine.measurement.export;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.polsl.hdised.engine.measurement.MeasurementRepository;
import pl.polsl.hdised.engine.measurement.export.model.FileExtension;
import pl.polsl.hdised.engine.measurement.export.strategy.ExportStrategyContext;
import pl.polsl.hdised.engine.measurement.model.MeasurementEntity;

@Service
public class MeasurementExportService {
  private final ExportStrategyContext<MeasurementEntity> strategyContext;
  private final MeasurementRepository measurementRepository;

  public MeasurementExportService(
      ExportStrategyContext<MeasurementEntity> strategyContext,
      MeasurementRepository measurementRepository) {
    this.strategyContext = strategyContext;
    this.measurementRepository = measurementRepository;
  }

  public Resource export(
      FileExtension fileExtension,
      String deviceId,
      String location,
      String stringStartDate,
      String stringFinishDate)
      throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.ENGLISH);
    Date startDate = format.parse(stringStartDate + ":00.000");
    Date finishDate = format.parse(stringFinishDate + ":00.000");
    return strategyContext
        .getStrategy(fileExtension)
        .export(
            measurementRepository
                .getAllByDateEntityScanDateBetweenAndDeviceEntityDeviceIdAndLocationEntity_City(
                    startDate, finishDate, deviceId, location));
  }
}
