package pl.polsl.hdised.consumer.measurement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;
import pl.polsl.hdised.consumer.measurement.MeasurementDto;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class MeasurementDtoDeserializer implements Deserializer<MeasurementDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public MeasurementDto deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), MeasurementDto.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to measurement");
        }
    }

    @Override
    public void close() {
    }
}
