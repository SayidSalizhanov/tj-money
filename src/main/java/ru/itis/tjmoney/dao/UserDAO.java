package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.dao.interfaces.IUserDAO;
import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private final Connection connection = ConnectionManager.getConnection();

    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM Users WHERE email = ?";
    private static final String FIND_BY_USERNAME_SQL = "SELECT * FROM Users WHERE username = ?";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM Users WHERE id = ?";
    private static final String SAVE_SQL = "INSERT INTO Users (username, email, password, telegram_id, sending_to_telegram, sending_to_email) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE Users SET username = ?, telegram_id = ?, sending_to_telegram = ?, sending_to_email = ? WHERE id = ?";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE Users SET password = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM Users WHERE id = ?";

    @Override
    public User findById(int id) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL);) {
            statement.setInt(1, id);
            return getUser(statement);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public User findByEmail(String email) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_SQL);) {
            statement.setString(1, email);
            return getUser(statement);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public User findByUsername(String username) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_SQL);) {
            statement.setString(1, username);
            return getUser(statement);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private User getUser(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("telegram_id"),
                        resultSet.getBoolean("sending_to_telegram"),
                        resultSet.getBoolean("sending_to_email")
                );
            }
            return null;
        }
    }

    @Override
    public User save(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, null);
            statement.setBoolean(5, false);
            statement.setBoolean(6, false);

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);

            return new User(id, user.getUsername(), user.getEmail(), user.getPassword(), null, false, false);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void update(User updatedUser) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, updatedUser.getUsername());
            statement.setString(2, updatedUser.getTelegramId());
            statement.setBoolean(3, updatedUser.isSendingToTelegram());
            statement.setBoolean(4, updatedUser.isSendingToEmail());
            statement.setInt(5,updatedUser.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void updatePassword(String newPassword, int userId) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD_SQL)) {
            statement.setString(1, newPassword);
            statement.setInt(2, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
