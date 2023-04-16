package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    private final DataSource dataSource;

    public SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        StringBuilder resultQuery = new StringBuilder();
        if (!checkIfExist(tableName)) return null;

        resultQuery.append("SELECT ");
        List<String> columnName = getAllColumnName(tableName);
        for (String name : columnName) {
            resultQuery.append(name);
            resultQuery.append(", ");
        }
        resultQuery.delete(resultQuery.length() - 2, resultQuery.length() - 1);
        resultQuery.append("FROM ");
        resultQuery.append(tableName);

        return resultQuery.toString();
    }

    @Override
    public List<String> getTables() throws SQLException {
        List<String> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet tables = metaData.getTables(null, null, "%", types);
            while (tables.next()) {
                result.add(tables.getString("TABLE_NAME"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private boolean checkIfExist(String tableName) {
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

    private List<String> getAllColumnName(String tableName) {
        List<String> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
            while (resultSet.next()) {
                result.add(resultSet.getString("COLUMN_NAME"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
