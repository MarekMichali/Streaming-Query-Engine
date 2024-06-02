package pl.polsl.hdised.engine.measurement.export.strategy;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.polsl.hdised.engine.measurement.export.model.Exportable;

@Service
public class CsvExportService<T extends Exportable> implements ExportService<T> {
  @Override
  public Resource export(List<T> data) {
    String content = prepareHeaders(data.iterator().next()) + prepareContent(data);

    return new ByteArrayResource(content.getBytes(StandardCharsets.UTF_8));
  }

  private String prepareHeaders(T object) {
    var headers = new StringBuilder();
    object.getHeadersRow().forEach(h -> headers.append(h).append(","));
    headers.append("\n");

    return headers.toString();
  }

  private String prepareContent(Collection<T> data) {
    var content = new StringBuilder();
    data.forEach(d -> content.append(prepareRow(d)));

    return content.toString();
  }

  private String prepareRow(T object) {
    var row = new StringBuilder();
    object.getColumns().forEach(column -> row.append(column).append(","));
    row.append("\n");

    return row.toString();
  }
}
