package pl.polsl.hdised.consumer.service;

import org.springframework.stereotype.Service;

@Service
public class TransformerService {

   /* public TemperatureDto transformToKelvinsAndAddToDb(TemperatureDto temperatureDto) {
        if (temperatureDto.getUnit().equals("kelvin")) {
            return temperatureDto;
        }
        temperatureDto.setTemperature(temperatureDto.getTemperature() + 273.15f);
        temperatureDto.setUnit("kelvins");

        return temperatureDto;
    }
*/
}
