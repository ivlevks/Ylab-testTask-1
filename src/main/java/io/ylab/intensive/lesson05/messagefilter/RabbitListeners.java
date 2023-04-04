package io.ylab.intensive.lesson05.messagefilter;

import io.ylab.intensive.lesson05.eventsourcing.repository.Repository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Component
public class RabbitListeners {

    @RabbitListener(queues = "input")
    public void processOrder(String data) {
        System.out.println("Received message: " + data);
    }

}
