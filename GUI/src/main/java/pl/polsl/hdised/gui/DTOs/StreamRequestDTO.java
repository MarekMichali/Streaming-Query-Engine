package pl.polsl.hdised.gui.DTOs;

public class StreamRequestDTO {
    private String device;
    private String location;

    public StreamRequestDTO(String device, String location) {
        this.device = device;
        this.location = location;
    }

    public StreamRequestDTO() {
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
}
