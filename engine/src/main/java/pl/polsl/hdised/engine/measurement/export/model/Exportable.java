package pl.polsl.hdised.engine.measurement.export.model;

import java.util.Collection;

public interface Exportable {
  Collection<String> getColumns();

  Collection<String> getHeadersRow();
}
