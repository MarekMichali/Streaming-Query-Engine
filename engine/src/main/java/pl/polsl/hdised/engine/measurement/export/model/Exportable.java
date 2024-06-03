package pl.polsl.hdised.engine.measurement.export.model;

import java.util.List;

public interface Exportable {
  List<String> getColumns();

  List<String> getHeadersRow();
}
