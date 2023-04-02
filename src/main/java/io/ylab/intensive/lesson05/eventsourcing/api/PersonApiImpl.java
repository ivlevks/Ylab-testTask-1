package io.ylab.intensive.lesson05.eventsourcing.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public class PersonApiImpl implements PersonApi {
    private final CachingConnectionFactory connectionFactory;
    private DataSource dataSource;
    private RabbitTemplate template;
    private final String exchangeName = "exc";
    private final String queueName = "queue";

    public PersonApiImpl(CachingConnectionFactory connectionFactory, DataSource dataSource, RabbitTemplate template) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
        this.template = template;
    }

    @Override
    public void deletePerson(Long personId) {

        template.convertAndSend("person-exchange", "person.delete", String.valueOf(personId));

//        try (Connection connection = connectionFactory.newConnection();
//             Channel channel = connection.createChannel()) {
//            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
//            channel.queueDeclare(queueName, true, false, false, null);
//            channel.basicPublish(exchangeName, "delete", null, String.valueOf(personId).getBytes());
//        } catch (IOException | TimeoutException e) {
//            throw new RuntimeException(e);
//        }
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


//        try (Connection connection = connectionFactory.newConnection();
//             Channel channel = connection.createChannel()) {
//            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
//            channel.queueDeclare(queueName, true, false, false, null);
//            channel.basicPublish(exchangeName, "save", null, personAsString.getBytes());
//        } catch (IOException | TimeoutException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public Person findPerson(Long personId) {
        Person resultPerson;
        String findPerson = "SELECT * from person WHERE person_id=?";

        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findPerson)) {
            preparedStatement.setInt(1, personId.intValue());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No person with Id " + personId);
                return null;
            }
            resultSet.next();
            resultPerson = createPerson(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultPerson;
    }

    @Override
    public List<Person> findAll() {
        List<Person> resultListPerson = new ArrayList<>();
        String findAll = "SELECT * from person";

        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAll)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No person exists in DB");
                return resultListPerson;
            }
            while (resultSet.next()) {
                Person currentPerson = createPerson(resultSet);
                resultListPerson.add(currentPerson);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultListPerson;
    }

    private static Person createPerson(ResultSet resultSet) throws SQLException {
        Person result = new Person();
        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = resultSet.getMetaData().getColumnName(i);
            String columnValue = resultSet.getString(i);
            if (columnName.equals("person_id")) result.setId(Long.parseLong(columnValue));
            if (columnName.equals("first_name")) result.setName(columnValue);
            if (columnName.equals("last_name")) result.setLastName(columnValue);
            if (columnName.equals("middle_name")) result.setMiddleName(columnValue);
        }
        return result;
    }
}
