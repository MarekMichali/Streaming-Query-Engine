package pl.polsl.hdised.engine.measurement.export.strategy;

import java.util.Map;
import org.springframework.stereotype.Component;
import pl.polsl.hdised.engine.measurement.export.model.Exportable;
import pl.polsl.hdised.engine.measurement.export.model.FileExtension;

@Component
public class ExportStrategyContext<T extends Exportable> {
  private final Map<FileExtension, ExportService<T>> strategies;

  public ExportStrategyContext(
      TxtExportService<T> measurementTxtExportService,
      CsvExportService<T> measurementCsvExportService,
      XlsxExportService<T> measurementXlsxExportService) {
    this.strategies =
        Map.of(
            FileExtension.TXT,
            measurementTxtExportService,
            FileExtension.CSV,
            measurementCsvExportService,
            FileExtension.XLSX,
            measurementXlsxExportService);
  }

  public ExportService<T> getStrategy(FileExtension fileExtension) {
    return strategies.get(fileExtension);
  }
}
