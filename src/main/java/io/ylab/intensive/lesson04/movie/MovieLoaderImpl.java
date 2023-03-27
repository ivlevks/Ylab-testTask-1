package io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class MovieLoaderImpl implements MovieLoader {
    private DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             Scanner scanner = new Scanner(fileInputStream)) {

            while (scanner.hasNextLine()) {
                String stringFromFile = scanner.nextLine();
                String[] data = stringFromFile.split(";");

                if (data[0].equals("Year") || data[0].equals("INT")) continue;

                Movie movie = createMovie(data);
                writeMovieInDB(movie);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Movie createMovie(String[] data) {
        Movie movie = new Movie();

        movie.setYear(checkEmptyString(data[0]) ? null : Integer.valueOf(data[0]));
        movie.setLength(checkEmptyString(data[1]) ? null : Integer.valueOf(data[1]));
        movie.setTitle(checkEmptyString(data[2]) ? null : data[2]);
        movie.setSubject(checkEmptyString(data[3]) ? null : data[3]);
        movie.setActors(checkEmptyString(data[4]) ? null : data[4]);
        movie.setActress(checkEmptyString(data[5]) ? null : data[5]);
        movie.setDirector(checkEmptyString(data[6]) ? null : data[6]);
        movie.setPopularity(checkEmptyString(data[7]) ? null : Integer.valueOf(data[7]));
        movie.setAwards(checkEmptyString(data[8]) ? null : data[8].equals("Yes"));

        return movie;
    }


    private void writeMovieInDB(Movie movie) {
        String writeMovie = "INSERT INTO movie (year, length, title, subject, actors, actress, " +
                "director, popularity, awards)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(writeMovie)) {

            if (movie.getYear() != null) {
                preparedStatement.setInt(1, movie.getYear());
            } else writeNull(preparedStatement, 1, "Integer");

            if (movie.getLength() != null) {
                preparedStatement.setInt(2, movie.getLength());
            } else writeNull(preparedStatement, 2, "Integer");

            if (movie.getTitle() != null) {
                preparedStatement.setString(3, movie.getTitle());
            } else writeNull(preparedStatement, 3, "String");

            if (movie.getSubject() != null) {
                preparedStatement.setString(4, movie.getSubject());
            } else writeNull(preparedStatement, 4, "String");

            if (movie.getActors() != null) {
                preparedStatement.setString(5, movie.getActors());
            } else writeNull(preparedStatement, 5, "String");

            if (movie.getActress() != null) {
                preparedStatement.setString(6, movie.getActress());
            } else writeNull(preparedStatement, 6, "String");

            if (movie.getDirector() != null) {
                preparedStatement.setString(7, movie.getDirector());
            } else writeNull(preparedStatement, 7, "String");

            if (movie.getPopularity() != null) {
                preparedStatement.setInt(8, movie.getPopularity());
            } else writeNull(preparedStatement, 8, "Integer");

            if (movie.getAwards() != null) {
                preparedStatement.setBoolean(9, movie.getAwards());
            } else writeNull(preparedStatement, 9, "Boolean");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void writeNull(PreparedStatement preparedStatement, int index, String type) {
        try {
            if (type.equals("Integer")) {
                preparedStatement.setNull(index, Types.INTEGER);
            }
            if (type.equals("String")) {
                preparedStatement.setNull(index, Types.VARCHAR);
            }
            if (type.equals("Boolean")) {
                preparedStatement.setNull(index, Types.BOOLEAN);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkEmptyString(String data) {
        return data.equals("");
    }
}
