package pl.polsl.hdised.producer.model;

import org.apache.tomcat.jni.Time;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pl.polsl.hdised.producer.dto.TemperatureDto;

import java.util.Calendar;
import java.util.Date;
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
                TemperatureDto temperatureDto = createMessage();
                System.out.println(temperatureDto.getCityName());
                System.out.println(temperatureDto.getDeviceId());
                System.out.println(temperatureDto.getDate());
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

    private TemperatureDto createMessage(){
        String city;
        switch(new Random().nextInt(0, 3)){
            case 0 -> city = "Gliwice";
            case 1 -> city = "Katowice";
            case 2 -> city = "Warszawa";
            default -> city = "Wroclaw";
        }
        String deviceId;
        switch(new Random().nextInt(0, 3)){
            case 0 -> deviceId = "dev01";
            case 1 -> deviceId = "dev02";
            case 2 -> deviceId = "dev03";
            default -> deviceId = "dev04";
        }
        Float temperature = new Random().nextFloat(-30.0f, 30.0f);
        return new TemperatureDto(city, Calendar.getInstance().getTime(), deviceId, temperature, "Celsius");

    }

}
