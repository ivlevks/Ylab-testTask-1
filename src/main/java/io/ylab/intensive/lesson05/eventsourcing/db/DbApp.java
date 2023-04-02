package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.repository.Repository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class DbApp {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        // тут пишем создание и запуск приложения работы с БД
        AmqpTemplate template = applicationContext.getBean(AmqpTemplate.class);
//        RabbitListeners listeners = applicationContext.getBean(RabbitListeners.class);

        while (true) {
            String message = (String) template.receiveAndConvert("saveQueue");
            if (message != null) {
                System.out.println(message);
            }
        }


//        Repository repository = applicationContext.getBean(Repository.class);
//        while (true) {
//            Message message = template.receive("myqueue");
//            System.out.println(message.getMessageProperties().getReceivedRoutingKey());
//
////            String person = (String) template.receiveAndConvert("myqueue");
////            if (person != null) {
////                System.out.println(person);
////                repository.savePerson(person);
////            }
//        }
    }
}
