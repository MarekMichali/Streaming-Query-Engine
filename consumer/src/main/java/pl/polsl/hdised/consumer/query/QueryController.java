package pl.polsl.hdised.consumer.query;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.polsl.hdised.consumer.averageresponse.AverageResponseDto;
import pl.polsl.hdised.consumer.exception.ParametersNotFoundException;

import java.text.ParseException;

@RequestMapping("api/v1/query")
@RestController
public class QueryController {

    private QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    public AverageResponseDto getHistoricalAverage(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location, @RequestParam("stringDate") String stringDate) throws ParseException, ParametersNotFoundException {
        return this.queryService.getHistoricalAverage(deviceId, location, stringDate);
    }

    @PostMapping("/set-query-parameters")
    public String setQueryParameters(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location, @RequestParam("stringDate") String stringDate) throws ParseException, ParametersNotFoundException {
        this.queryService.setQueryParameters(deviceId, location, stringDate);

        return "Parameters set successfully";
    }

    @GetMapping("/get-average")
    public Float getStreamAverage() {
        return this.queryService.getStreamAverage();
    }

    @ExceptionHandler(ParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleException(ParseException parseException) {
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
}
