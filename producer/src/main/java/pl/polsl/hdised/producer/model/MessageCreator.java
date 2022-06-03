package pl.polsl.hdised.producer.model;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pl.polsl.hdised.producer.dto.TemperatureDto;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MessageCreator {

    private AtomicBoolean produce;

    // IF WE WANT TO WORK WITH DIFFERENT CLASS THAN STRING WE NEED TO CHANGE THIS SECOND ARGUMENT
    private KafkaTemplate<String, TemperatureDto> temperatureKafkaTemplate;


    public MessageCreator(KafkaTemplate<String, TemperatureDto> temperatureKafkaTemplate) {
        this.temperatureKafkaTemplate = temperatureKafkaTemplate;
        this.produce = new AtomicBoolean(true);
    }

    public synchronized void setProduce(boolean produce) {
        this.produce.set(produce);
    }

    public void StartProducingMessages() throws InterruptedException {
        while (true) {
            if (this.produce.get()) {
                TemperatureDto temperatureDto = new TemperatureDto((new Random().nextFloat(-30.0f, 30.0f)), "Celsius degrees");
                System.out.println(temperatureDto.getUnit());
                System.out.println(temperatureDto.getTemperature());
                System.out.println("----------------------------");
                temperatureKafkaTemplate.send("topic", temperatureDto);
                Thread.sleep(500);
            } else {
                break;
            }
        }
    }

}
