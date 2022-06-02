package pl.polsl.hdised.producer.service;

import org.springframework.stereotype.Service;
import pl.polsl.hdised.producer.model.MessageCreator;

@Service
public class MessageService {

    private MessageCreator messageCreator;

    public MessageService(MessageCreator messageCreator) {
        this.messageCreator = messageCreator;
    }

    public void startProducing() {
        this.messageCreator.setProduce(true);
        Thread thread = new Thread(() -> {
            try {
                this.messageCreator.StartProducingMessages();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void stopProducing() {
        this.messageCreator.setProduce(false);
    }

}
