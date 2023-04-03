package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.repository.Repository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Component
public class RabbitListeners {
    private Repository repository;

    public RabbitListeners(Repository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = {"saveQueue", "deleteQueue"})
    public void processOrder(String data, @Header(AmqpHeaders.CONSUMER_QUEUE) String queue) {
        System.out.println("Received message: " + data + " from " + queue);

        if (queue.equals("saveQueue")) repository.savePerson(data);
        if (queue.equals("deleteQueue")) repository.deletePerson(Long.valueOf(data));
    }
}
