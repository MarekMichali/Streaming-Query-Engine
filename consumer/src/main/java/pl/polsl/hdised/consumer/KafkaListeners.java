package pl.polsl.hdised.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.polsl.hdised.consumer.dto.TemperatureDto;


@Component
public class KafkaListeners {


    // IF DATA WILL BE OUR CUSTOM CLASS WE WILL PASS THERE THAT CLASS
    @KafkaListener(topics = "topic", groupId = "hdised", containerFactory = "kafkaListenerContainerFactory")
    void listener(TemperatureDto data) {
        System.out.println("----------------------------------------------");
        System.out.println(data.getTemperature().toString());
    }

}
