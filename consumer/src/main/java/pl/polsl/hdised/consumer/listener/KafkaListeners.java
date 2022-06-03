package pl.polsl.hdised.consumer.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.polsl.hdised.consumer.dto.TemperatureDto;
import pl.polsl.hdised.consumer.entity.Temperature;
import pl.polsl.hdised.consumer.repository.TemperatureRepository;
import pl.polsl.hdised.consumer.service.TransformerService;


@Component
public class KafkaListeners {

    private TransformerService transformerService;
    private TemperatureRepository temperatureRepository;

    public KafkaListeners(TransformerService transformerService, TemperatureRepository temperatureRepository) {
        this.transformerService = transformerService;
        this.temperatureRepository = temperatureRepository;
    }

    // IF DATA WILL BE OUR CUSTOM CLASS WE WILL PASS THERE THAT CLASS
    @KafkaListener(topics = "topic", groupId = "hdised", containerFactory = "kafkaListenerContainerFactory")
    void listener(TemperatureDto temperatureDto) {
        temperatureDto = this.transformerService.transformToKelvinsAndAddToDb(temperatureDto);
        System.out.println(temperatureDto.getUnit());
        System.out.println(temperatureDto.getTemperature());
        System.out.println("----------------------------");

        temperatureRepository.save(new Temperature(temperatureDto.getUnit(), temperatureDto.getTemperature()));

    }

}
