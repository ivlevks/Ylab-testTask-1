package io.ylab.intensive.lesson05.eventsourcing.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.repository.Repository;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class PersonApiImpl implements PersonApi {
    private final RabbitTemplate template;
    private final Repository repository;

    public PersonApiImpl(CachingConnectionFactory connectionFactory, DataSource dataSource,
                         RabbitTemplate template, Repository repository) {
        this.template = template;
        this.repository = repository;
    }

    @Override
    public void deletePerson(Long personId) {
        template.convertAndSend("person-exchange", "person.delete", String.valueOf(personId));
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        Person person = new Person(personId, firstName, lastName, middleName);
        String personAsString;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            personAsString = objectMapper.writeValueAsString(person);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        template.convertAndSend("person-exchange", "person.save", personAsString);
    }

    @Override
    public Person findPerson(Long personId) {
        return repository.findPerson(personId);
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }
}
