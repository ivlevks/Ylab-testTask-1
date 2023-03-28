package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;

public class ApiApp {
    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = initMQ();

        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        PersonApi personApi = new PersonApiImpl(connectionFactory);
        personApi.savePerson(1L, "Kostik", "Ivlev", "Stanislavovich");
        personApi.savePerson(2L, "Ivanov", "Ivan", "Ivanovich");
        personApi.savePerson(3L, "Ivanova", "Anna", "Nicolaevna");
        personApi.savePerson(1L, "Konstantin", "Ivlev", "Stanislavovich");
        personApi.deletePerson(2L);
        personApi.deletePerson(5L);
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}
