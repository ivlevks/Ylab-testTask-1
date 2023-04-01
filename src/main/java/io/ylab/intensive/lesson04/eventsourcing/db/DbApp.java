package io.ylab.intensive.lesson04.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DbApp {
    public static void main(String[] args) throws Exception {
        DataSource dataSource = initDb();
        ConnectionFactory connectionFactory = initMQ();
        String queueName = "queue";

        // тут пишем создание и запуск приложения работы с БД
        try (Connection connectionRMQ = connectionFactory.newConnection();
             Channel channel = connectionRMQ.createChannel()) {
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(queueName, true);
                if (message != null) {
                    String receivedMessage = new String(message.getBody());
                    String routingKey = message.getEnvelope().getRoutingKey();

                    if (routingKey.equals("save")) savePersonToDB(receivedMessage, dataSource);
                    if (routingKey.equals("delete")) deletePersonFromDB(receivedMessage, dataSource);
                }
            }
        }
    }


    private static void savePersonToDB(String receivedMessage, DataSource dataSource) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = objectMapper.readValue(receivedMessage, Person.class);
        Long personId = person.getId();

        String savePerson = "INSERT INTO person (person_id, first_name, last_name, middle_name) " +
                "VALUES (?, ?, ?, ?)";
        if (checkPersonIfExist(personId, dataSource)) {
            updatePersonFromDB(person, dataSource);
        } else {
            try (java.sql.Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(savePerson)) {
                preparedStatement.setInt(1, personId.intValue());
                preparedStatement.setString(2, person.getName());
                preparedStatement.setString(3, person.getLastName());
                preparedStatement.setString(4, person.getMiddleName());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static void updatePersonFromDB(Person person, DataSource dataSource) {
        String updatePerson = "UPDATE person SET first_name=?, last_name=?, middle_name=? " +
                "WHERE person_id=?";

        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updatePerson)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getMiddleName());
            preparedStatement.setInt(4, person.getId().intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static void deletePersonFromDB(String receivedMessage, DataSource dataSource) {
        Long personId = Long.valueOf(receivedMessage);

        if (!checkPersonIfExist(personId, dataSource)) {
            System.out.println("Delete Person with Id - " + personId + " is not available. " +
                    "Person is not exist.");
        } else {
            String deletePerson = "DELETE from person WHERE person_id=?";
            try (java.sql.Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(deletePerson)) {
                preparedStatement.setInt(1, personId.intValue());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static boolean checkPersonIfExist(Long personId, DataSource dataSource) {
        boolean result = false;
        String checkIfExist = "SELECT EXISTS(SELECT * from person WHERE person_id=?)";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(checkIfExist)) {
            preparedStatement.setInt(1, personId.intValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getObject(1, Boolean.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "CREATE TABLE if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
