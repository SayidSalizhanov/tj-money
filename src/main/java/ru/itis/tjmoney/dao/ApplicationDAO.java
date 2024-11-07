package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.Application;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {
    private static String FIND_APPLICATIONS_BY_USERID_SQL = "SELECT * FROM Applications WHERE user_id = ?";
    private static String DELETE_APPLICATION_BY_USERID_SQL = "DELETE * FROM Applications WHERE user_id = ?";

    public List<Application> findUserApplications(int userId) {
        List<Application> applications = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_APPLICATIONS_BY_USERID_SQL)) {
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                applications.add(
                        new Application(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getInt("group_id"),
                                resultSet.getTimestamp("send_at").toLocalDateTime(),
                                resultSet.getString("status")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return applications;
    }

    public void deleteApplicationByUserId(int userId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_APPLICATION_BY_USERID_SQL)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}