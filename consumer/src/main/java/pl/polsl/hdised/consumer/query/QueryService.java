package pl.polsl.hdised.consumer.query;


import org.springframework.stereotype.Service;
import pl.polsl.hdised.consumer.averageresponse.AverageResponseDto;
import pl.polsl.hdised.consumer.measurement.MeasurementRepository;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

@Service
public class QueryService {

    private MeasurementRepository measurementRepository;

    public QueryService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public AverageResponseDto getHistoricalAverage(String deviceId, String location, Date date){
        validateParams(deviceId, location, date);
        Tuple tuple = this.measurementRepository.getAverage(deviceId, location, date);
        Double avgTemp = (Double) tuple.get("averageTemperature");
        BigInteger tempsCount = (BigInteger) tuple.get("temperaturesCount");
        return new AverageResponseDto(avgTemp.floatValue(), tempsCount.intValue());
    }

    public void setQueryParameters(String deviceId, String location, Date date){
        validateParams(deviceId, location, date);
        Query query = Query.getInstance();
        query.setParameters(location, deviceId, date);
    }

    public Float getStreamAverage(){
        Query query = Query.getInstance();

        return query.getTemperaturesSum() / query.getTemperaturesCount();
    }

    private void validateParams(String deviceId, String location, Date date){
        Objects.requireNonNull(deviceId, "device id cannot be null");
        Objects.requireNonNull(location, "location cannot be null");
        Objects.requireNonNull(date, "date id cannot be null");
    }

}
