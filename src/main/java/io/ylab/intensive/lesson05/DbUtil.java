package io.ylab.intensive.lesson05;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class DbUtil {

    public static void applyDdl(String ddl, DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(ddl);
        }
    }

    /*
     * Настройки подключения НЕ МЕНЯЕМ!
     * Надо настроить БД таким образом, чтобы она работала со следующими
     * настройками
     */
    public static DataSource buildDataSource() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDatabaseName("postgres");
        dataSource.setPortNumber(5432);
        dataSource.getConnection().close();
        return dataSource;
    }

    public static boolean checkDbIfExists(String tableName, DataSource dataSource) {
        boolean result = false;
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet tables = metaData.getTables(null, null, tableName, types);
            result = tables.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static void writeDataFromDb(String tableName, DataSource dataSource, List<String> data) {
        String writeData = "INSERT INTO " + tableName + " (word) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(writeData)) {

            for (String word : data) {
                preparedStatement.setString(1, word);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
