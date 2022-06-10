package pl.polsl.hdised.consumer.query;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.polsl.hdised.consumer.averageresponse.AverageResponseDto;
import pl.polsl.hdised.consumer.device.DeviceDto;
import pl.polsl.hdised.consumer.exception.ParametersNotFoundException;
import pl.polsl.hdised.consumer.exception.EmptyMeasurementsException;
import pl.polsl.hdised.consumer.location.LocationDto;
import pl.polsl.hdised.consumer.temperatureResponse.TemperatureResponseDto;

import javax.persistence.Tuple;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/v1/query")
@RestController
public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }


    @GetMapping
    public AverageResponseDto getHistoricalAverage(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location, @RequestParam("startDate") String stringStartDate, @RequestParam("finishDate") String stringFinishDate) throws ParseException, ParametersNotFoundException {
        return this.queryService.getHistoricalAverage(deviceId, location, stringStartDate, stringFinishDate);
    }

    @PostMapping("/set-query-parameters")
    public String setQueryParameters(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location) throws ParametersNotFoundException {
        this.queryService.setQueryParameters(deviceId, location);

        return "Parameters set successfully";
    }

    @GetMapping("/temperatures")
    public List<TemperatureResponseDto> getAllTemperatures(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location, @RequestParam("startDate") String stringStartDate, @RequestParam("finishDate") String stringFinishDate) throws ParseException {
        return this.queryService.getAllTemperatures(deviceId, location, stringStartDate, stringFinishDate);
    }

    @GetMapping("/average")
    public Float getStreamAverage() throws EmptyMeasurementsException {
        return this.queryService.getStreamAverage();
    }

    @GetMapping("/minimum-temperature")
    public Float getMinimumTemperature(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location, @RequestParam("startDate") String stringStartDate, @RequestParam("finishDate") String stringFinishDate) throws ParseException, ParametersNotFoundException {
        return this.queryService.getMinimumTemperature(deviceId, location, stringStartDate, stringFinishDate);
    }

    @GetMapping("/maximum-temperature")
    public Float getMaximumTemperature(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location, @RequestParam("startDate") String stringStartDate, @RequestParam("finishDate") String stringFinishDate) throws ParseException, ParametersNotFoundException {
        return this.queryService.getMaximumTemperature(deviceId, location, stringStartDate, stringFinishDate);
    }

    @GetMapping("/devices")
    public List<DeviceDto> getDevices() {
        return this.queryService.getDevices();
    }

    @GetMapping("/locations")
    public List<LocationDto> getLocations() {
        return this.queryService.getLocations();
    }


    @ExceptionHandler(ParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleException() {
        return "Provided date format is invalid";
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleException(NullPointerException nullPointerException) {
        return nullPointerException.getMessage();
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
