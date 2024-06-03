package pl.polsl.hdised.engine.measurement.export.model;

public enum FileExtension {
  TXT("text/plain", "exported.txt"),
  CSV("text/csv", "exported.csv"),
  XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "exported.xlsx");

  public final String contentType;
  public final String fileName;

  FileExtension(String contentType, String fileName) {
    this.contentType = contentType;
    this.fileName = fileName;
  }
}
