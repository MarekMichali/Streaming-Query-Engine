package pl.polsl.hdised.measurementgenerator.measurement;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MeasurementCreator {

    private AtomicBoolean produce;

    // IF WE WANT TO WORK WITH DIFFERENT CLASS THAN STRING WE NEED TO CHANGE THIS SECOND ARGUMENT
    private KafkaTemplate<String, MeasurementDto> measurementKafkaTemplate;


    public MeasurementCreator(KafkaTemplate<String, MeasurementDto> measurementKafkaTemplate) {
        this.measurementKafkaTemplate = measurementKafkaTemplate;
        this.produce = new AtomicBoolean(true);
    }

    public synchronized void setProduce(boolean produce) {
        this.produce.set(produce);
    }

    public void StartProducingMeasurementsToDatabase(String topic) throws InterruptedException {
        while (true) {
            if (this.produce.get()) {
                MeasurementDto measurementDto = createMeasurement();
                System.out.println(measurementDto.getCityName());
                System.out.println(measurementDto.getDeviceId());
                System.out.println(measurementDto.getDate());
                System.out.println(measurementDto.getUnit());
                System.out.println(measurementDto.getTemperature());
                System.out.println("----------------------------");
                measurementKafkaTemplate.send(topic, measurementDto);
                Thread.sleep(500);
            } else {
                break;
            }
        }
    }

    private MeasurementDto createMeasurement(){
        String city;
        switch(new Random().nextInt(0, 3)){
            case 0 -> city = "Gliwice";
            case 1 -> city = "Katowice";
            case 2 -> city = "Warszawa";
            default -> city = "Wroclaw";
        }
        String deviceClass;
        switch(new Random().nextInt(0, 3)){
            case 0 -> deviceClass = "dev01";
            case 1 -> deviceClass = "dev02";
            case 2 -> deviceClass = "dev03";
            default -> deviceClass = "dev04";
        }
        Float temperature = new Random().nextFloat(-30.0f, 30.0f);
        return new MeasurementDto(city, Calendar.getInstance().getTime(), deviceClass, temperature, "Celsius");

    }

}
