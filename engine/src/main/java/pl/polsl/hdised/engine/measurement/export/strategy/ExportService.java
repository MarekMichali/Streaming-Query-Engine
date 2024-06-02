package pl.polsl.hdised.engine.measurement.export.strategy;

import java.io.IOException;
import java.util.List;
import org.springframework.core.io.Resource;
import pl.polsl.hdised.engine.measurement.export.model.Exportable;

public interface ExportService<T extends Exportable> {

  Resource export(List<T> data) throws IOException;
}
