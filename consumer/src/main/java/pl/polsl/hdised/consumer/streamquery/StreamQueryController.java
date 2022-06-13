package pl.polsl.hdised.consumer.streamquery;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.polsl.hdised.consumer.exception.EmptyMeasurementsException;
import pl.polsl.hdised.consumer.exception.ParametersNotFoundException;
import pl.polsl.hdised.consumer.measurement.MeasurementDto;

import java.util.List;

@RestController
@RequestMapping("api/v1/query/stream")
public class StreamQueryController {

    private final StreamQueryService streamQueryService;

    public StreamQueryController(StreamQueryService streamQueryService) {
        this.streamQueryService = streamQueryService;
    }

    @PostMapping("/query-parameters")
    public String setQueryParameters(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location) throws ParametersNotFoundException {
        this.streamQueryService.setQueryParameters(deviceId, location);

        return "Parameters set successfully";
    }

    @GetMapping("/average-temperature")
    public Float getStreamAverageTemperature() throws EmptyMeasurementsException {
        return this.streamQueryService.getStreamAverage();
    }

    @GetMapping("/minimum-temperature")
    public Float getStreamMinimumTemperature() throws EmptyMeasurementsException {
        return this.streamQueryService.getStreamMinimumTemperature();
    }

    @GetMapping("/maximum-temperature")
    public Float getStreamMaximumTemperature() throws EmptyMeasurementsException {
        return this.streamQueryService.getStreamMaximumTemperature();
    }

    @GetMapping("/temperatures")
    public List<Object> getMeasurements(){
        return this.streamQueryService.getMeasurements();
    }


    @ExceptionHandler(ParametersNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleException(ParametersNotFoundException parametersNotFoundException) {
        return parametersNotFoundException.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleException(EmptyMeasurementsException emptyMeasurementsException) {
        return emptyMeasurementsException.getMessage();
    }

}
