package pl.polsl.hdised.producer.measurement;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MeasurementCreator {

    private AtomicBoolean produce;

    // IF WE WANT TO WORK WITH DIFFERENT CLASS THAN STRING WE NEED TO CHANGE THIS SECOND ARGUMENT
    private KafkaTemplate<String, MeasurementDto> temperatureKafkaTemplate;


    public MeasurementCreator(KafkaTemplate<String, MeasurementDto> temperatureKafkaTemplate) {
        this.temperatureKafkaTemplate = temperatureKafkaTemplate;
        this.produce = new AtomicBoolean(true);
    }

    public synchronized void setProduce(boolean produce) {
        this.produce.set(produce);
    }

    public void StartProducingMessages() throws InterruptedException {
        while (true) {
            if (this.produce.get()) {
                MeasurementDto measurementDto = createMessage();
                System.out.println(measurementDto.getCityName());
                System.out.println(measurementDto.getDeviceId());
                System.out.println(measurementDto.getDate());
                System.out.println(measurementDto.getUnit());
                System.out.println(measurementDto.getTemperature());
                System.out.println("----------------------------");
                temperatureKafkaTemplate.send("topic", measurementDto);
                Thread.sleep(500);
            } else {
                break;
            }
        }
    }

    private MeasurementDto createMessage(){
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
        return new MeasurementDto(city, Calendar.getInstance().getTime(), deviceId, temperature, "Celsius");

    }

}
