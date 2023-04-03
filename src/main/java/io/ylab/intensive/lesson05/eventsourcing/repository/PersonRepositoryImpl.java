package io.ylab.intensive.lesson05.eventsourcing.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonRepositoryImpl implements Repository {
    private DataSource dataSource;

    public PersonRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) {
        if (!checkPersonIfExist(personId)) {
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

    private Person createPerson(ResultSet resultSet) throws SQLException {
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
