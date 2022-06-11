package pl.polsl.hdised.gui.DTOs;

import java.util.ArrayList;

public class MultipleValueDatabaseResponseDTO {
    private ArrayList <TemperatureResponseDTO> values;
    public ArrayList<TemperatureResponseDTO> getValues() {
        return values;
    }

    public void setValues(ArrayList<TemperatureResponseDTO> values) {
        this.values = values;
    }
}
