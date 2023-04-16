package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;


public class ApiApp {
    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        // пишем взаимодействие с PersonApi
        personApi.savePerson(1L, "Ivlev", "Kostik", "Stanislavovich");
        personApi.savePerson(1L, "Ivlev", "Kostik", "Stanislavovich");
        personApi.savePerson(2L, "Ivanov", "Ivan", "Ivanovich");
        personApi.savePerson(3L, "Ivanova", "Anna", "Nicolaevna");
        personApi.savePerson(4L, "Petrov", "Petr", "Ivanovich");
        personApi.savePerson(5L, "Sidorov", "Foma", "Dmitrievich");
        personApi.savePerson(1L, "Ivlev", "Konstantin", "Stanislavovich");
        personApi.deletePerson(1L);
        personApi.deletePerson(4L);

        List<Person> personList = personApi.findAll();
        System.out.println(personList.size());
        Person person = personApi.findPerson(5L);
        System.out.println(person.getName());
    }
}
