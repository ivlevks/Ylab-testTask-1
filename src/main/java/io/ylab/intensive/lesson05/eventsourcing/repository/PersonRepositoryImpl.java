package io.ylab.intensive.lesson05.eventsourcing.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class PersonRepositoryImpl implements Repository {
    private DataSource dataSource;

    public PersonRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) {

    }

    @Override
    public void savePerson(String receivedMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person;
        try {
            person = objectMapper.readValue(receivedMessage, Person.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Long personId = person.getId();

        String savePerson = "INSERT INTO person (person_id, first_name, last_name, middle_name) " +
                "VALUES (?, ?, ?, ?)";
        if (checkPersonIfExist(personId)) {
            updatePersonFromDB(person);
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

    @Override
    public Person findPerson(Long personId) {
        return null;
    }

    @Override
    public List<Person> findAll() {
        return null;
    }

    private boolean checkPersonIfExist(Long personId) {
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

    private void updatePersonFromDB(Person person) {
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
}
