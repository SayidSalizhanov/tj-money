package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.dao.interfaces.IAvatarDAO;
import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvatarDAO implements IAvatarDAO {
    private final Connection connection = ConnectionManager.getConnection();

    private static final String FIND_URL_BY_USER_ID_SQL = "SELECT * FROM avatars WHERE user_id = ?";
    private static final String SAVE_SQL = "INSERT INTO avatars (user_id, url) values (?, ?)";
    private static final String UPDATE_AVATAR_URL_SQL = "UPDATE avatars SET url = ? WHERE user_id = ?";
    private static final String DELETE_SQL = "DELETE FROM avatars WHERE user_id = ?";

    @Override
    public String findUrl(int userId) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_URL_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("url");
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void save(int userId, String url) {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_SQL)) {
            statement.setInt(1, userId);
            statement.setString(2, url);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void update(int userId, String url) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_AVATAR_URL_SQL)) {
            statement.setString(1, url);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void delete(int userId) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
