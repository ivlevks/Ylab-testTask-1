package io.ylab.intensive.lesson04.persistentmap;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private DataSource dataSource;
    private String mapName;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.mapName = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        boolean result = false;
        String containsKey = "SELECT EXISTS(SELECT * from persistent_map WHERE map_name=? AND KEY=?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(containsKey)) {

            preparedStatement.setString(1, mapName);
            preparedStatement.setString(2, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getObject(1, Boolean.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> resultList = new ArrayList<>();
        String get = "SELECT key from persistent_map WHERE map_name=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(get)) {

            preparedStatement.setString(1, mapName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultList;
    }

    @Override
    public String get(String key) throws SQLException {
        if (!containsKey(key)) return null;
        String result;
        String get = "SELECT value from persistent_map WHERE map_name=? AND KEY=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(get)) {

            preparedStatement.setString(1, mapName);
            preparedStatement.setString(2, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public void remove(String key) throws SQLException {
        String remove = "DELETE from persistent_map WHERE map_name=? AND KEY=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(remove)) {

            preparedStatement.setString(1, mapName);
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {

        if (containsKey(key)) remove(key);

        String put = "INSERT INTO persistent_map (map_name, KEY, value) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(put)) {
            preparedStatement.setString(1, mapName);
            preparedStatement.setString(2, key);
            preparedStatement.setString(3, value);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() throws SQLException {
        String clear = "DELETE from persistent_map WHERE map_name=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(clear)) {

            preparedStatement.setString(1, mapName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
