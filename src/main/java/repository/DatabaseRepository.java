package repository;

import domain.BaseEntity;
import domain.modules.SQLElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    private static final String URL = "jdbc:postgresql://localhost:5432/Bookstore";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";

    private String tableName;
    private SQLElement<ID, T> sqlElement;

    public DatabaseRepository(String tableName, SQLElement<ID, T> sqlElement) {
        this.tableName = tableName;
        this.sqlElement = sqlElement;
    }

    @Override
    public Optional<T> findOne(ID id) {
        List<T> elements = new ArrayList<>();
        String query = "SELECT * FROM " + tableName + " WHERE id=" + id.toString();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                elements.add(sqlElement.fromSQL(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(elements.isEmpty())
            return Optional.empty();
        else
            return Optional.of(elements.get(0));
    }

    @Override
    public Iterable<T> findAll() {
        List<T> elements = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                elements.add(sqlElement.fromSQL(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elements;
    }

    @Override
    public Optional<T> save(T entity) {
        String query = sqlElement.insertSQL(entity);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.of(entity);
    }

    @Override
    public Optional<T> delete(ID id) {
        Optional<T> elem = findOne(id);

        String sql = "DELETE FROM "+ tableName + " WHERE id=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return elem;
    }

    @Override
    public Optional<T> update(T entity) {
        String query = sqlElement.updateSQL(entity);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.of(entity);
    }
}
