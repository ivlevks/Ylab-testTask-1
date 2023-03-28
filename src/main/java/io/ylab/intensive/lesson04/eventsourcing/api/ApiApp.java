package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import java.util.List;

public class ApiApp {
    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = initMQ();

        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        PersonApi personApi = new PersonApiImpl(connectionFactory);
//        personApi.savePerson(1L, "Ivlev", "Kostik", "Stanislavovich");
//        personApi.savePerson(2L, "Ivanov", "Ivan", "Ivanovich");
//        personApi.savePerson(3L, "Ivanova", "Anna", "Nicolaevna");
//        personApi.savePerson(4L, "Petrov", "Petr", "Ivanovich");
//        personApi.savePerson(5L, "Sidorov", "Foma", "Dmitrievich");
//        personApi.savePerson(1L, "Ivlev", "Konstantin", "Stanislavovich");
        personApi.deletePerson(1L);
        personApi.deletePerson(3L);
        personApi.deletePerson(4L);
        personApi.deletePerson(5L);
        personApi.deletePerson(7L);
//        Person person = personApi.findPerson(1L);
//        List<Person> personList = personApi.findAll();
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}
