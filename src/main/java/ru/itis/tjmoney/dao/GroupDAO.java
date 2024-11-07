package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.Group;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    private static final String FIND_BY_ID_SQL = "SELECT * FROM Groups WHERE id = ?";
    private static final String SAVE_SQL = "INSERT INTO Groups (name, created_at, description) VALUES (?, ?, ?)";

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
            statement.setTimestamp(2, Timestamp.valueOf(group.getCreated_at()));
            statement.setString(3, group.getDescription());

            statement.executeUpdate();

            int id = statement.getGeneratedKeys().getInt(1);

            return new Group(id, group.getName(), group.getCreated_at(), group.getDescription());
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
