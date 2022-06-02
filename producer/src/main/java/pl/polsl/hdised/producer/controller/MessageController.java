package pl.polsl.hdised.producer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.hdised.producer.service.MessageService;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/stop-producing")
    public String stopProducing() {
        this.messageService.stopProducing();
        return "Producing stopped";
    }

    @PostMapping("/start-producing")
    public String startProducing() throws InterruptedException {
        this.messageService.startProducing();
        return "Producing started";
    }

}

