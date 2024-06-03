package pl.polsl.hdised.engine.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.polsl.hdised.engine.measurement.model.MeasurementDto;
import pl.polsl.hdised.engine.query.stream.StreamQueryService;

@Component
public class KafkaListeners {

  private final StreamQueryService streamQueryService;

  public KafkaListeners(StreamQueryService streamQueryService) {
    this.streamQueryService = streamQueryService;
  }

  // IF DATA WILL BE OUR CUSTOM CLASS WE WILL PASS THERE THAT CLASS
  @KafkaListener(
      topics = "topic",
      groupId = "hdised",
      containerFactory = "kafkaListenerContainerFactory")
  void listener(MeasurementDto measurementDto) {
    this.streamQueryService.addMeasurement(measurementDto);
  }

  @KafkaListener(
      topics = "streamTopic",
      groupId = "hdised",
      containerFactory = "kafkaListenerContainerFactory")
  void streamListener(MeasurementDto measurementDto) {
    this.streamQueryService.UpdateQuery(measurementDto);
  }
}
