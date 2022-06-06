package pl.polsl.hdised.consumer.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.polsl.hdised.consumer.measurement.MeasurementDto;
import pl.polsl.hdised.consumer.query.QueryService;


@Component
public class KafkaListeners {

    private final QueryService queryService;

    public KafkaListeners(QueryService queryService) {
        this.queryService = queryService;
    }

    // IF DATA WILL BE OUR CUSTOM CLASS WE WILL PASS THERE THAT CLASS
    @KafkaListener(topics = "topic", groupId = "hdised", containerFactory = "kafkaListenerContainerFactory")
    void listener(MeasurementDto measurementDto) {
        this.queryService.addMeasurement(measurementDto);
    }

    @KafkaListener(topics = "streamTopic", groupId = "hdised", containerFactory = "kafkaListenerContainerFactory")
    void streamListener(MeasurementDto measurementDto) {
        this.queryService.UpdateQuery(measurementDto);
    }

}
