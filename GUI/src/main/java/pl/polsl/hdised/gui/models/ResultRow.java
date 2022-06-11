package pl.polsl.hdised.gui.models;

public class ResultRow {
    private String startDate;
    private String finishDate;
    private String device;
    private String location;
    private String value;

    public ResultRow(String startDate, String finishDate, String device, String location, String value) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.device = device;
        this.location = location;
        this.value = value;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
            this.startDate = startDate;
    }
}
