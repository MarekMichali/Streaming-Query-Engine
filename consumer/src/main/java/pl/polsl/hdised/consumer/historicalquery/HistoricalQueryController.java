package pl.polsl.hdised.consumer.historicalquery;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.polsl.hdised.consumer.averageresponse.AverageResponseDto;
import pl.polsl.hdised.consumer.device.DeviceDto;
import pl.polsl.hdised.consumer.exception.ParametersNotFoundException;
import pl.polsl.hdised.consumer.exception.EmptyMeasurementsException;
import pl.polsl.hdised.consumer.location.LocationDto;
import pl.polsl.hdised.consumer.temperatureResponse.TemperatureResponseDto;

import java.text.ParseException;
import java.util.List;

@RequestMapping("api/v1/query/historical")
@RestController
public class HistoricalQueryController {

    private final HistoricalQueryService historicalQueryService;

    public HistoricalQueryController(HistoricalQueryService historicalQueryService) {
        this.historicalQueryService = historicalQueryService;
    }


    @GetMapping("/average")
    public AverageResponseDto getHistoricalAverage(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location, @RequestParam("startDate") String stringStartDate, @RequestParam("finishDate") String stringFinishDate) throws ParseException, ParametersNotFoundException {
        return this.historicalQueryService.getHistoricalAverage(deviceId, location, stringStartDate, stringFinishDate);
    }

    @GetMapping("/temperatures")
    public List<TemperatureResponseDto> getAllTemperatures(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location, @RequestParam("startDate") String stringStartDate, @RequestParam("finishDate") String stringFinishDate) throws ParseException {
        return this.historicalQueryService.getAllHistoricalTemperatures(deviceId, location, stringStartDate, stringFinishDate);
    }

    @GetMapping("/minimum-temperature")
    public Float getMinimumHistoricalTemperature(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location, @RequestParam("startDate") String stringStartDate, @RequestParam("finishDate") String stringFinishDate) throws ParseException, ParametersNotFoundException {
        return this.historicalQueryService.getMinimumHistoricalTemperature(deviceId, location, stringStartDate, stringFinishDate);
    }

    @GetMapping("/maximum-temperature")
    public Float getMaximumHistoricalTemperature(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location, @RequestParam("startDate") String stringStartDate, @RequestParam("finishDate") String stringFinishDate) throws ParseException, ParametersNotFoundException {
        return this.historicalQueryService.getMaximumHistoricalTemperature(deviceId, location, stringStartDate, stringFinishDate);
    }

    @GetMapping("/devices")
    public List<DeviceDto> getDevices() {
        return this.historicalQueryService.getDevices();
    }

    @GetMapping("/locations")
    public List<LocationDto> getLocations() {
        return this.historicalQueryService.getLocations();
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
