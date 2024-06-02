package pl.polsl.hdised.engine.measurement.export.strategy;

import java.util.Collection;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.polsl.hdised.engine.measurement.export.model.Exportable;

@Service
public class XlsxExportService<T extends Exportable> implements ExportService<T> {
  @Override
  public Resource export(Collection<T> data) {
    return null;
  }
}
