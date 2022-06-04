package pl.polsl.hdised.consumer.query;


import org.springframework.stereotype.Service;
import pl.polsl.hdised.consumer.averageresponse.AverageResponseDto;
import pl.polsl.hdised.consumer.measurement.MeasurementRepository;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.Date;

@Service
public class QueryService {

    private MeasurementRepository measurementRepository;

    public QueryService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public AverageResponseDto getHistoricalAverage(String deviceId, String location, Date date){
        Tuple tuple = this.measurementRepository.getAverage(deviceId, location, date);
        Double avgTemp = (Double) tuple.get("averageTemperature");
        BigInteger tempsCount = (BigInteger) tuple.get("temperaturesCount");
        return new AverageResponseDto(avgTemp.floatValue(), tempsCount.intValue());
    }
}
