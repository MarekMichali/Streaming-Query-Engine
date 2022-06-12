package pl.polsl.hdised.gui.DTOs;

public class SingleValueStreamResponseDTO {
    private String device;
    private String location;
    private String value;

    public SingleValueStreamResponseDTO(String device, String location, String value) {
        this.device = device;
        this.location = location;
        this.value = value;
    }

    public SingleValueStreamResponseDTO() {
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
}
