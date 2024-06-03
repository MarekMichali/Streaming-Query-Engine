package pl.polsl.hdised.engine.measurement.export.strategy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.polsl.hdised.engine.measurement.export.model.Exportable;

@Service
public class XlsxExportService<T extends Exportable> implements ExportService<T> {
  @Override
  public Resource export(List<T> data) throws IOException {
    try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {

      Sheet sheet = workbook.createSheet("Dane pomiarowe");

      createdHeaderRow(sheet, data.iterator().next());

      for (int i = 0; i < data.size(); i++) {
        addRow(sheet, data.get(i), i + 1);
      }

      workbook.write(out);

      return new ByteArrayResource(out.toByteArray());
    }
  }

  private void createdHeaderRow(Sheet sheet, T object) {
    var headerRow = sheet.createRow(0);
    var values = object.getHeadersRow();

    for (int i = 0; i < values.size(); ++i) {
      headerRow.createCell(i).setCellValue(values.get(i));
    }
  }

  private void addRow(Sheet sheet, T object, int index) {
    Row row = sheet.createRow(index);
    var values = object.getColumns();

    for (int i = 0; i < values.size(); ++i) {
      row.createCell(i).setCellValue(values.get(i));
    }
  }
}
