package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String FIND_ALL_SQL = "SELECT * FROM Users";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM Users WHERE email = ?";
    private static final String FIND_BY_USERNAME_SQL = "SELECT * FROM Users WHERE username = ?";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM Users WHERE id = ?";
    private static final String SAVE_SQL = "INSERT INTO Users (username, email, password, telegram_id, sending_to_telegram, sending_to_email) values (?,?,?,?,?,?)";

    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
            while (resultSet.next()) {
                users.add(
                        new User(
                                resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("telegram_id"),
                                resultSet.getBoolean("sending_to_telegram"),
                                resultSet.getBoolean("sending_to_email")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return users;
    }

    public User findById(int id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL);) {
            statement.setInt(1, id);
            return getUser(statement);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public User findByEmail(String email) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_SQL);) {
            statement.setString(1, email);
            return getUser(statement);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public User findByUsername(String username) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_SQL);) {
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

    public User save(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, null);
            statement.setBoolean(5, false);
            statement.setBoolean(6, false);

            statement.executeUpdate();

            int id = statement.getGeneratedKeys().getInt(1);

            return new User(id, user.getUsername(), user.getEmail(), user.getPassword(), null, false, false);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
