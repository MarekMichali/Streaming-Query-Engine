package pl.polsl.hdised.producer.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.hdised.producer.dto.MessageRequest;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {

    // IF WE WANT TO WORK WITH DIFFERENT CLASS THAN STRING WE NEED TO CHANGE THIS SECOND ARGUMENT
    private KafkaTemplate<String, String> kafkaTemplate;

    public MessageController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void publish(@RequestBody MessageRequest messageRequest) {
        kafkaTemplate.send("topic", messageRequest.getMessage());
    }
}

