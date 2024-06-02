package pl.polsl.hdised.engine.measurement.export.strategy;

import java.util.Collection;

import org.springframework.core.io.Resource;
import pl.polsl.hdised.engine.measurement.export.model.Exportable;

public interface ExportService<T extends Exportable> {

  Resource export(Collection<T> data);
}
