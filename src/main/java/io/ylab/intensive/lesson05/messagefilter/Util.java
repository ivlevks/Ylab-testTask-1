package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Util {
    public final DataSource dataSource;

    public Util(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static List<String> getDataFromFile(String file) throws FileNotFoundException {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(new FileInputStream(file));
        while (scanner.hasNext()) {
            result.add(scanner.nextLine());
        }
        return result;
    }

    public String validateSentence(String data) {
        String result = data;
        String[] word = data.replaceAll("[,.;?!]", "").trim().split(" ");
        for (String currentWord : word) {
            String resultWord = validateWord(currentWord);
            result = result.replace(currentWord, resultWord);
        }
        return result;
    }

    public String validateWord(String data) {
        if (!checkWordIfExistInDb(data.toLowerCase())) return data;
        return changeWordOnCorrectVersion(data);
    }

    private String changeWordOnCorrectVersion(String data) {
        return data.charAt(0) +
                "*".repeat(Math.max(0, data.length() - 2)) +
                data.charAt(data.length() - 1);
    }

    private boolean checkWordIfExistInDb(String data) {
        boolean result;
        String checkWordIfExist = "SELECT EXISTS(SELECT * from profanity WHERE word=?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(checkWordIfExist)) {
            preparedStatement.setString(1, data);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getObject(1, Boolean.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void applyDdl(String ddl, DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(ddl);
        }
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
