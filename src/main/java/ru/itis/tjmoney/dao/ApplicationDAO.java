package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.dao.interfaces.IApplicationDAO;
import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.Application;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO implements IApplicationDAO {
    private static String FIND_APPLICATIONS_BY_USERID_SQL = "SELECT * FROM Applications WHERE user_id = ?";
    private static String FIND_APPLICATIONS_BY_GROUPID_SQL = "SELECT * FROM Applications WHERE group_id = ?";
    private static String DELETE_APPLICATION_BY_ID_SQL = "DELETE FROM Applications WHERE id = ?";
    private static String DELETE_BY_USER_ID_AND_GROUP_ID_SQL = "DELETE FROM Applications WHERE user_id = ? AND group_id = ?";
    private static String SAVE_APPLICATION_SQL = "INSERT INTO Applications (user_id, group_id, send_at, status) VALUES (?,?,?,?)";
    private static String UPDATE_APPLICATION_SQL = "UPDATE Applications SET status = ? WHERE id = ?";

    @Override
    public void update(Application application) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_APPLICATION_SQL)) {
            statement.setString(1, application.getStatus());
            statement.setInt(2, application.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
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

    @Override
    public List<Application> findGroupApplications(int groupId) {
        List<Application> applications = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_APPLICATIONS_BY_GROUPID_SQL)) {
            statement.setInt(1, groupId);

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

    @Override
    public void deleteByUserIdAndGroupId(int userId, int groupId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_USER_ID_AND_GROUP_ID_SQL)) {
            statement.setInt(1, userId);
            statement.setInt(2, groupId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void deleteApplicationById(int id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_APPLICATION_BY_ID_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void save(Application application) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_APPLICATION_SQL)) {
            statement.setInt(1, application.getUserId());
            statement.setInt(2, application.getGroupId());
            statement.setTimestamp(3, Timestamp.valueOf(application.getSendAt()));
            statement.setString(4, application.getStatus());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}