package io.ylab.intensive.lesson05.eventsourcing.db;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbApp {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        // тут пишем создание и запуск приложения работы с БД
        // логика работы с БД вынесена в пакет repository
        // вместе с контекстом поднимается RabbitListeners, который
        // направляет принятые сообщения в БД
    }
}
