package pl.polsl.hdised.consumer.query;


import org.springframework.stereotype.Service;
import pl.polsl.hdised.consumer.averageresponse.AverageResponseDto;
import pl.polsl.hdised.consumer.date.DateEntity;
import pl.polsl.hdised.consumer.date.DateRepository;
import pl.polsl.hdised.consumer.device.DeviceEntity;
import pl.polsl.hdised.consumer.device.DeviceRepository;
import pl.polsl.hdised.consumer.exception.ParametersNotFoundException;
import pl.polsl.hdised.consumer.location.LocationEntity;
import pl.polsl.hdised.consumer.location.LocationRepository;
import pl.polsl.hdised.consumer.measurement.MeasurementDto;
import pl.polsl.hdised.consumer.measurement.MeasurementEntity;
import pl.polsl.hdised.consumer.measurement.MeasurementRepository;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Service
public class QueryService {

    private final MeasurementRepository measurementRepository;
    private final DeviceRepository deviceRepository;
    private final LocationRepository locationRepository;
    private final DateRepository dateRepository;

    public QueryService(MeasurementRepository measurementRepository, DeviceRepository deviceRepository, LocationRepository locationRepository, DateRepository dateRepository) {
        this.measurementRepository = measurementRepository;
        this.deviceRepository = deviceRepository;
        this.locationRepository = locationRepository;
        this.dateRepository = dateRepository;
    }

    public AverageResponseDto getHistoricalAverage(String deviceId, String location, String stringStartDate, String stringFinishDate) throws ParseException, ParametersNotFoundException {
        if (!parametersExists(deviceId, location)) {
            throw new ParametersNotFoundException();
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.ENGLISH);
        Date startDate = format.parse(stringStartDate + ":00.000");
        Date finsishDate = format.parse(stringFinishDate + ":00.000");

        Tuple tuple = this.measurementRepository.getAverageAndCount(Objects.requireNonNull(deviceId, "device id cannot be null"), Objects.requireNonNull(location, "location cannot be null"), Objects.requireNonNull(startDate, "date cannot be null"), Objects.requireNonNull(finsishDate, "date cannot be null"));

        Double avgTemp = (Double) tuple.get("averageTemperature");
        BigInteger tempsCount = (BigInteger) tuple.get("temperaturesCount");
        return new AverageResponseDto(avgTemp.floatValue(), tempsCount.intValue());
    }

    public void setQueryParameters(String deviceId, String location) throws ParametersNotFoundException {
        if (!parametersExists(deviceId, location)) {
            throw new ParametersNotFoundException();
        }


        Query query = Query.getInstance();
        query.setParameters(Objects.requireNonNull(location, "location cannot be null"), Objects.requireNonNull(deviceId, "device id cannot be null"));
    }

    public Float getStreamAverage() {
        Query query = Query.getInstance();

        return query.getTemperaturesSum() / query.getTemperaturesCount();
    }

    public void UpdateQuery(MeasurementDto measurementDto){
        if (areParametersEqual(measurementDto)) {
            System.out.println("Parameters are equal, adding to Average...");
            Query.getInstance().appendMeasurement(measurementDto.getTemperature());
        }
    }

    private boolean areParametersEqual(MeasurementDto measurementDto) {
        return measurementDto.getCityName().equals(Query.getInstance().getLocation()) && measurementDto.getDeviceId().equals(Query.getInstance().getDeviceId());
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

    private synchronized void printMeasurement(MeasurementDto measurementDto) {
        System.out.println(measurementDto.getCityName());
        System.out.println(measurementDto.getDeviceId());
        System.out.println(measurementDto.getDate());
        System.out.println(measurementDto.getUnit());
        System.out.println(measurementDto.getTemperature());
        System.out.println("----------------------------");
    }

    private Boolean parametersExists(String deviceId, String location) {
        return this.deviceRepository.findDeviceById(deviceId) != null && this.locationRepository.findLocationByCity(location) != null;
    }

}
