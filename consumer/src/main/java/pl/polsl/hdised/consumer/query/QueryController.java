package pl.polsl.hdised.consumer.query;

import jdk.jfr.ContentType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.hdised.consumer.averageresponse.AverageResponseDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@RequestMapping("api/v1/query")
@RestController
public class QueryController {

    private QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    public AverageResponseDto getHistoricalAverage(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location, @RequestParam("stringDate") String stringDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = format.parse(stringDate);

        return this.queryService.getHistoricalAverage(deviceId, location, date);
    }

    @PostMapping("/set-parameters")
    public String setQueryParameters(@RequestParam("deviceId") String deviceId, @RequestParam("location") String location, @RequestParam("stringDate") String stringDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = format.parse(stringDate);

        this.queryService.setQueryParameters(deviceId, location, date);

        return "Parameters set successfully";
    }

    @GetMapping("/get-average")
    public Float getStreamAverage(){
        return this.queryService.getStreamAverage();
    }
}
