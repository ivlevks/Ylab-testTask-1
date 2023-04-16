package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class RabbitListeners {
    private final Util util;
    private final RabbitTemplate rabbitTemplate;

    public RabbitListeners(Util util, RabbitTemplate rabbitTemplate) {
        this.util = util;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "input")
    public void processOrder(String data) {
        String correctSentence = util.validateSentence(data);
        rabbitTemplate.convertAndSend("output", correctSentence);
    }
}
