package pl.polsl.hdised.producer.measurement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/measurements")
public class MeasurementController {

    private MeasurementService measurementService;

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
        this.measurementService.startProducingToDatabase();
        return "Producing started";
    }

}

