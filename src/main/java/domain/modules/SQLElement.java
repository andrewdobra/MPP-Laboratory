package domain.modules;

import domain.BaseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLElement<ID, T extends BaseEntity<ID>>
{
    public T fromSQL(ResultSet resultSet) throws SQLException {
        return null;
    }

    public String insertSQL(T t) {
        return null;
    }

    public String updateSQL(T t) {
        return null;
    }
}
