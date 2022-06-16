package pl.polsl.hdised.measurementgenerator.measurement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/measurements")
public class MeasurementController {

    private final String TOPIC = "topic";
    private final String STREAM_TOPIC = "streamTopic";

    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping("/stop-producing")
    public String stopProducing() {
        this.measurementService.stopProducing();
        return "Producing stopped";
    }

    @PostMapping("/start-producing-to-database")
    public String startProducingToDatabase() {
        this.measurementService.startProducing(TOPIC);
        return "Producing started";
    }

    @PostMapping("/start-stream-producing")
    public String startStreamProducing() {
        this.measurementService.startProducing(STREAM_TOPIC);
        return "Producing started";
    }

}

