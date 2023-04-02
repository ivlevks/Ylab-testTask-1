package io.ylab.intensive.lesson05.eventsourcing.db;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public class RabbitListeners {

    @RabbitListener(queues = "saveQueue")
    public void receiveMessageFromSaveQueue(String message) {
        System.out.println("Received save message: " + message);
    }

    @RabbitListener(queues = "deleteQueue")
    public void receiveMessageFromDeleteQueue(String message) {
        System.out.println("Received delete message: " + message);
    }

}
