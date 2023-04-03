package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {

    public SQLQueryBuilderImpl(DataSource dataSource) {
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        return null;
    }

    @Override
    public List<String> getTables() throws SQLException {
        return null;
    }
}
