package pl.polsl.hdised.consumer.query;


import org.springframework.stereotype.Service;
import pl.polsl.hdised.consumer.averageresponse.AverageResponseDto;
import pl.polsl.hdised.consumer.device.DeviceRepository;
import pl.polsl.hdised.consumer.exception.ParametersNotFoundException;
import pl.polsl.hdised.consumer.location.LocationRepository;
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

    public QueryService(MeasurementRepository measurementRepository, DeviceRepository deviceRepository, LocationRepository locationRepository) {
        this.measurementRepository = measurementRepository;
        this.deviceRepository = deviceRepository;
        this.locationRepository = locationRepository;
    }

    public AverageResponseDto getHistoricalAverage(String deviceId, String location, String stringDate) throws ParseException, ParametersNotFoundException {
        if (!parametersExists(deviceId, location)) {
            throw new ParametersNotFoundException();
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = format.parse(stringDate);

        Tuple tuple = this.measurementRepository.getAverage(Objects.requireNonNull(deviceId, "device id cannot be null"), Objects.requireNonNull(location, "location cannot be null"), Objects.requireNonNull(date, "date cannot be null"));

        Double avgTemp = (Double) tuple.get("averageTemperature");
        BigInteger tempsCount = (BigInteger) tuple.get("temperaturesCount");
        return new AverageResponseDto(avgTemp.floatValue(), tempsCount.intValue());
    }

    public void setQueryParameters(String deviceId, String location, String stringDate) throws ParseException, ParametersNotFoundException {
        if (!parametersExists(deviceId, location)) {
            throw new ParametersNotFoundException();
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = format.parse(stringDate);

        Query query = Query.getInstance();
        query.setParameters(Objects.requireNonNull(location, "location cannot be null"), Objects.requireNonNull(deviceId, "device id cannot be null"), Objects.requireNonNull(date, "date cannot be null"));
    }

    public Float getStreamAverage() {
        Query query = Query.getInstance();

        return query.getTemperaturesSum() / query.getTemperaturesCount();
    }

    private Boolean parametersExists(String deviceId, String location) {
        return this.deviceRepository.findDeviceById(deviceId) != null && this.locationRepository.findLocationByCity(location) != null;
    }

}
