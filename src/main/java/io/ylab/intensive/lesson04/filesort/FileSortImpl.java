package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class FileSortImpl implements FileSorter {
    private DataSource dataSource;
    private int batchSize = 10_000;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {
        File result = new File("result.txt");
        writeNumberToDB(data);
        writeNumbersFromDBToResultFile(result);
        return result;
    }


    private void writeNumberToDB(File data) {
        String writeNumber = "INSERT INTO numbers (val) VALUES (?)";
        Long number = null;

        try (FileInputStream fileInputStream = new FileInputStream(data);
             Scanner scanner = new Scanner(fileInputStream);
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(writeNumber)) {
            connection.setAutoCommit(false);

            while (scanner.hasNextLong()) {
                int count = 0;
                while (scanner.hasNextLong() && count++ < batchSize) {
                    number = scanner.nextLong();
                    preparedStatement.setLong(1, number);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                connection.commit();
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void writeNumbersFromDBToResultFile(File result) {
        try (PrintWriter printWriter = new PrintWriter(result);
             Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("SELECT * FROM numbers ORDER BY val DESC");

            while (rs.next()) {
                printWriter.println(rs.getLong(1));
            }

            printWriter.flush();
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
