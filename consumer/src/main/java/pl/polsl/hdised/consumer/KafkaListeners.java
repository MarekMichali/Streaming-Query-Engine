package pl.polsl.hdised.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    // IF DATA WILL BE OUR CUSTOM CLASS WE WILL PASS THERE THAT CLASS
    @KafkaListener(topics = "topic", groupId = "hdised")
    void listener(String data) {
        System.out.println("Listener received - " + data);
    }

}
