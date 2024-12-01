package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.Group;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    private static final String FIND_ALL_SQL = "SELECT * FROM Groups";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM Groups WHERE id = ?";
    private static final String SAVE_SQL = "INSERT INTO Groups (name, created_at, description) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE Groups SET name = ?, description = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM Groups WHERE id = ?";

    public void update(Group updatedGroup) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, updatedGroup.getName());
            statement.setString(2, updatedGroup.getDescription());
            statement.setInt(3, updatedGroup.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public List<Group> findAll() {
        List<Group> groups = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL);

            while (resultSet.next()) {
                groups.add(
                        new Group(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getTimestamp("created_at").toLocalDateTime(),
                                resultSet.getString("description")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return groups;
    }

    public Group findById(int groupId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, groupId);
            return getGroup(statement);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private Group getGroup(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return new Group(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getString("description")
                );
            }
            return null;
        }
    }

    public Group save(Group group) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, group.getName());
            statement.setTimestamp(2, Timestamp.valueOf(group.getCreatedAt()));
            statement.setString(3, group.getDescription());

            statement.executeUpdate();

            int id = statement.getGeneratedKeys().getInt(1);

            return new Group(id, group.getName(), group.getCreatedAt(), group.getDescription());
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public void delete(int groupId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, groupId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
