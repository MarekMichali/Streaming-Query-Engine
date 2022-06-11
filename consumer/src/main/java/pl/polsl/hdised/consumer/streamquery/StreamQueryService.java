package pl.polsl.hdised.consumer.streamquery;

import org.springframework.stereotype.Service;
import pl.polsl.hdised.consumer.date.DateEntity;
import pl.polsl.hdised.consumer.date.DateRepository;
import pl.polsl.hdised.consumer.device.DeviceEntity;
import pl.polsl.hdised.consumer.device.DeviceRepository;
import pl.polsl.hdised.consumer.exception.EmptyMeasurementsException;
import pl.polsl.hdised.consumer.exception.ParametersNotFoundException;
import pl.polsl.hdised.consumer.location.LocationEntity;
import pl.polsl.hdised.consumer.location.LocationRepository;
import pl.polsl.hdised.consumer.measurement.MeasurementDto;
import pl.polsl.hdised.consumer.measurement.MeasurementEntity;
import pl.polsl.hdised.consumer.measurement.MeasurementRepository;

import java.util.Date;
import java.util.Objects;

@Service
public class StreamQueryService {

    private final DeviceRepository deviceRepository;
    private final LocationRepository locationRepository;
    private final DateRepository dateRepository;
    private final MeasurementRepository measurementRepository;

    public StreamQueryService(DeviceRepository deviceRepository, LocationRepository locationRepository, DateRepository dateRepository, MeasurementRepository measurementRepository) {
        this.deviceRepository = deviceRepository;
        this.locationRepository = locationRepository;
        this.dateRepository = dateRepository;
        this.measurementRepository = measurementRepository;
    }

    public void setQueryParameters(String deviceId, String location) throws ParametersNotFoundException {
        Objects.requireNonNull(location, "location cannot be null");
        Objects.requireNonNull(deviceId, "device id cannot be null");

        if (!parametersExists(deviceId, location)) {
            throw new ParametersNotFoundException();
        }

        StreamQuery streamQuery = StreamQuery.getInstance();
        streamQuery.setParameters(location, deviceId);
    }

    private Boolean parametersExists(String deviceId, String location) {
        return !Objects.isNull(this.deviceRepository.findDeviceById(deviceId)) && !Objects.isNull(this.locationRepository.findLocationByCity(location));
    }

    public Float getStreamAverage() throws EmptyMeasurementsException {
        StreamQuery streamQuery = StreamQuery.getInstance();
        if (Objects.equals(streamQuery.getTemperaturesCount(), 0)) {
            throw new EmptyMeasurementsException();
        }
        return streamQuery.getTemperaturesSum() / streamQuery.getTemperaturesCount();
    }

    public void UpdateQuery(MeasurementDto measurementDto) {
        if (areParametersEqualToQuery(measurementDto)) {
            System.out.println("Parameters are equal, adding to Average...");
            StreamQuery.getInstance().appendMeasurement(measurementDto.getTemperature());
        }
    }

    private boolean areParametersEqualToQuery(MeasurementDto measurementDto) {
        return measurementDto.getCityName().equals(StreamQuery.getInstance().getLocation()) && measurementDto.getDeviceId().equals(StreamQuery.getInstance().getDeviceId());
    }

    public void addMeasurement(MeasurementDto measurementDto) {
        printMeasurement(measurementDto);

        DeviceEntity deviceEntity = this.addDevice(measurementDto.getDeviceId());

        LocationEntity locationEntity = this.addLocation(measurementDto.getCityName());

        DateEntity dateEntity = this.addDate(measurementDto.getDate());

        MeasurementEntity measurementEntity = new MeasurementEntity(deviceEntity, locationEntity, dateEntity, measurementDto.getUnit(), measurementDto.getTemperature());
        measurementRepository.save(measurementEntity);
    }

    private DeviceEntity addDevice(String deviceId) {
        DeviceEntity deviceEntity = deviceRepository.findDeviceById(deviceId);
        if (Objects.isNull(deviceEntity)) {
            deviceEntity = new DeviceEntity(deviceId);
            this.deviceRepository.save(deviceEntity);
        }
        return deviceEntity;
    }

    private LocationEntity addLocation(String city) {
        LocationEntity locationEntity = this.locationRepository.findLocationByCity(city);
        if (Objects.isNull(locationEntity)) {
            locationEntity = new LocationEntity(city);
            this.locationRepository.save(locationEntity);
        }
        return locationEntity;
    }

    private DateEntity addDate(Date date) {
        DateEntity dateEntity = new DateEntity(date);
        this.dateRepository.save(dateEntity);

        return dateEntity;
    }

    private void printMeasurement(MeasurementDto measurementDto) {
        System.out.println(measurementDto.getCityName());
        System.out.println(measurementDto.getDeviceId());
        System.out.println(measurementDto.getDate());
        System.out.println(measurementDto.getUnit());
        System.out.println(measurementDto.getTemperature());
        System.out.println("----------------------------");
    }

    public Float getStreamMinimumTemperature() throws EmptyMeasurementsException {
        StreamQuery streamQuery = StreamQuery.getInstance();
        if (Objects.equals(streamQuery.getTemperaturesCount(), 0)) {
            throw new EmptyMeasurementsException();
        }
        return streamQuery.getMinimumTemperature();
    }

    public Float getStreamMaximumTemperature() throws EmptyMeasurementsException {
        StreamQuery streamQuery = StreamQuery.getInstance();
        if (Objects.equals(streamQuery.getTemperaturesCount(), 0)) {
            throw new EmptyMeasurementsException();
        }
        return streamQuery.getMaximumTemperature();
    }
}
