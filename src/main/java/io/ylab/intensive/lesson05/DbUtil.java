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
}
