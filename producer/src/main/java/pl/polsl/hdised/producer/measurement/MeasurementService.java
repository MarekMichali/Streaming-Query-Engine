package pl.polsl.hdised.producer.measurement;

import org.springframework.stereotype.Service;
import pl.polsl.hdised.producer.measurement.MeasurementCreator;

@Service
public class MeasurementService {

    private MeasurementCreator measurementCreator;

    public MeasurementService(MeasurementCreator measurementCreator) {
        this.measurementCreator = measurementCreator;
    }

    public void startProducing() {
        this.measurementCreator.setProduce(true);
        Thread thread = new Thread(() -> {
            try {
                this.measurementCreator.StartProducingMessages();
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
