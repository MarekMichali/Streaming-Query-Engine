package pl.polsl.hdised.producer.measurement;

import org.springframework.stereotype.Service;

@Service
public class MeasurementService {

    private MeasurementCreator measurementCreator;

    public MeasurementService(MeasurementCreator measurementCreator) {
        this.measurementCreator = measurementCreator;
    }

    public void startProducing(String topic) {
        this.measurementCreator.setProduce(true);
        Thread thread = new Thread(() -> {
            try {
                this.measurementCreator.StartProducingMeasurementsToDatabase(topic);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void stopProducing() {
        this.measurementCreator.setProduce(false);
    }

}
