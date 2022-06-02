package pl.polsl.hdised.producer.model;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MessageCreator {

    private AtomicBoolean produce;

    // IF WE WANT TO WORK WITH DIFFERENT CLASS THAN STRING WE NEED TO CHANGE THIS SECOND ARGUMENT
    private KafkaTemplate<String, String> kafkaTemplate;


    public MessageCreator(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.produce = new AtomicBoolean(true);
    }

    public synchronized void setProduce(boolean produce) {
        this.produce.set(produce);
    }

    public void StartProducingMessages() throws InterruptedException {
        while (true) {
            if (this.produce.get()) {
                kafkaTemplate.send("topic", Integer.toString(new Random().nextInt()));
                Thread.sleep(10);
            } else {
                break;
            }
        }
    }

}
