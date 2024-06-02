package pl.polsl.hdised.engine.measurement.export.model;

public enum FileExtension {
  TXT(".txt"),
  CSV(".csv"),
  XLSX(".xlsx");

  public final String value;

  FileExtension(String value) {
    this.value = value;
  }
}
