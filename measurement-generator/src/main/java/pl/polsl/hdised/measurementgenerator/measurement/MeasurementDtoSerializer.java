package pl.polsl.hdised.measurementgenerator.measurement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MeasurementDtoSerializer implements Serializer<MeasurementDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, MeasurementDto temperature) {
        try {
            if (temperature == null) {
                return null;
            }
            System.out.println("Serializing");
            return objectMapper.writeValueAsBytes(temperature);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing Temperature to byte[]");
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, MeasurementDto data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
