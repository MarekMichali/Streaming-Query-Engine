package pl.polsl.hdised.consumer.streamquery;

import org.springframework.web.bind.annotation.*;
import pl.polsl.hdised.consumer.exception.EmptyMeasurementsException;
import pl.polsl.hdised.consumer.exception.ParametersNotFoundException;

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

    @GetMapping("/average")
    public Float getStreamAverage() throws EmptyMeasurementsException {
        return this.streamQueryService.getStreamAverage();
    }

}
