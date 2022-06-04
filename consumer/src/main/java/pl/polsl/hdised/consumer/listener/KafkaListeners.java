package pl.polsl.hdised.consumer.listener;

import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.polsl.hdised.consumer.dto.TemperatureDto;
import pl.polsl.hdised.consumer.entity.Device;
import pl.polsl.hdised.consumer.entity.Location;
import pl.polsl.hdised.consumer.entity.ScanDate;
import pl.polsl.hdised.consumer.entity.Temperature;
import pl.polsl.hdised.consumer.repository.DeviceRepository;
import pl.polsl.hdised.consumer.repository.LocationRepository;
import pl.polsl.hdised.consumer.repository.ScanDateRepository;
import pl.polsl.hdised.consumer.repository.TemperatureRepository;
import pl.polsl.hdised.consumer.service.TransformerService;


@Component
public class KafkaListeners {

    private TransformerService transformerService;
    private TemperatureRepository temperatureRepository;
    private DeviceRepository deviceRepository;
    private LocationRepository locationRepository;
    private ScanDateRepository scanDateRepository;

    public KafkaListeners(TransformerService transformerService, TemperatureRepository temperatureRepository, DeviceRepository deviceRepository, LocationRepository locationRepository, ScanDateRepository scanDateRepository) {
        this.transformerService = transformerService;
        this.temperatureRepository = temperatureRepository;
        this.deviceRepository = deviceRepository;
        this.locationRepository = locationRepository;
        this.scanDateRepository = scanDateRepository;
    }

    // IF DATA WILL BE OUR CUSTOM CLASS WE WILL PASS THERE THAT CLASS
    @KafkaListener(topics = "topic", groupId = "hdised", containerFactory = "kafkaListenerContainerFactory")
    void listener(TemperatureDto temperatureDto) {
        //temperatureDto = this.transformerService.transformToKelvinsAndAddToDb(temperatureDto);
        System.out.println(temperatureDto.getCityName());
        System.out.println(temperatureDto.getDeviceId());
        System.out.println(temperatureDto.getDate());
        System.out.println(temperatureDto.getUnit());
        System.out.println(temperatureDto.getTemperature());
        System.out.println("----------------------------");

        Device device = deviceRepository.findDeviceById(temperatureDto.getDeviceId());
        if(device == null){
            device = new Device(temperatureDto.getDeviceId());
            this.deviceRepository.save(device);
        }

        Location location = this.locationRepository.findLocationByCity(temperatureDto.getCityName());
        if(location == null){
            location = new Location(temperatureDto.getCityName());
            this.locationRepository.save(location);
        }

        ScanDate scanDate = new ScanDate(temperatureDto.getDate());
        this.scanDateRepository.save(scanDate);

        Temperature temperature = new Temperature(device, location, scanDate, temperatureDto.getUnit(), temperatureDto.getTemperature());
        temperatureRepository.save(temperature);

    }
}
