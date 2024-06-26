package pl.polsl.hdised.engine.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import pl.polsl.hdised.engine.measurement.model.MeasurementDto;
import pl.polsl.hdised.engine.measurement.model.MeasurementDtoDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaEngineConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  public Map<String, Object> consumerConfig() {
    Map<String, Object> properties = new HashMap<>();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    return properties;
  }

  // SECOND ARGUMENT IS WHAT WE WANT TO PRODUCE(OBJECT, OUR CLASS etc.)
  @Bean
  public ConsumerFactory<String, MeasurementDto> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(
        consumerConfig(), new StringDeserializer(), new MeasurementDtoDeserializer());
  }

  // SECOND ARGUMENT IS WHAT WE WANT TO PRODUCE(OBJECT, OUR CLASS etc.)
  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MeasurementDto>>
      kafkaListenerContainerFactory(ConsumerFactory<String, MeasurementDto> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, MeasurementDto> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    return factory;
  }
}
