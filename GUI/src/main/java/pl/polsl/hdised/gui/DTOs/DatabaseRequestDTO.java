package pl.polsl.hdised.gui.DTOs;

public class DatabaseRequestDTO {
    private String device;
    private String location;
    private String startDate;
    private String finishDate;

    public DatabaseRequestDTO(String device, String location, String startDate, String finishDate) {
        this.device = device;
        this.location = location;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public DatabaseRequestDTO() {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
}
