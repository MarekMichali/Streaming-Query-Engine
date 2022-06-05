package pl.polsl.hdised.consumer.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.polsl.hdised.consumer.date.DateEntity;
import pl.polsl.hdised.consumer.date.DateRepository;
import pl.polsl.hdised.consumer.device.DeviceEntity;
import pl.polsl.hdised.consumer.device.DeviceRepository;
import pl.polsl.hdised.consumer.location.LocationEntity;
import pl.polsl.hdised.consumer.location.LocationRepository;
import pl.polsl.hdised.consumer.measurement.MeasurementDto;
import pl.polsl.hdised.consumer.measurement.MeasurementEntity;
import pl.polsl.hdised.consumer.measurement.MeasurementRepository;
import pl.polsl.hdised.consumer.query.QueryService;


@Component
public class KafkaListeners {

    private QueryService queryService;
    private MeasurementRepository measurementRepository;
    private DeviceRepository deviceRepository;
    private LocationRepository locationRepository;
    private DateRepository dateRepository;

    public KafkaListeners(QueryService queryService, MeasurementRepository measurementRepository, DeviceRepository deviceRepository, LocationRepository locationRepository, DateRepository dateRepository) {
        this.queryService = queryService;
        this.measurementRepository = measurementRepository;
        this.deviceRepository = deviceRepository;
        this.locationRepository = locationRepository;
        this.dateRepository = dateRepository;
    }

    // IF DATA WILL BE OUR CUSTOM CLASS WE WILL PASS THERE THAT CLASS
    @KafkaListener(topics = "topic", groupId = "hdised", containerFactory = "kafkaListenerContainerFactory")
    void listener(MeasurementDto measurementDto) {
        printMeasurement(measurementDto);

        DeviceEntity deviceEntity = deviceRepository.findDeviceById(measurementDto.getDeviceId());
        if (deviceEntity == null) {
            deviceEntity = new DeviceEntity(measurementDto.getDeviceId());
            this.deviceRepository.save(deviceEntity);
        }

        LocationEntity locationEntity = this.locationRepository.findLocationByCity(measurementDto.getCityName());
        if (locationEntity == null) {
            locationEntity = new LocationEntity(measurementDto.getCityName());
            this.locationRepository.save(locationEntity);
        }

        DateEntity dateEntity = new DateEntity(measurementDto.getDate());
        this.dateRepository.save(dateEntity);

        MeasurementEntity measurementEntity = new MeasurementEntity(deviceEntity, locationEntity, dateEntity, measurementDto.getUnit(), measurementDto.getTemperature());
        measurementRepository.save(measurementEntity);

    }

    @KafkaListener(topics = "streamTopic", groupId = "hdised", containerFactory = "kafkaListenerContainerFactory")
    void streamListener(MeasurementDto measurementDto) {
        printMeasurement(measurementDto);
    }

    private synchronized void printMeasurement(MeasurementDto measurementDto) {
        System.out.println(measurementDto.getCityName());
        System.out.println(measurementDto.getDeviceId());
        System.out.println(measurementDto.getDate());
        System.out.println(measurementDto.getUnit());
        System.out.println(measurementDto.getTemperature());
        System.out.println("----------------------------");
    }
}
