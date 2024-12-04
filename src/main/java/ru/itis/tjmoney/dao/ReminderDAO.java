package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.Goal;
import ru.itis.tjmoney.models.Reminder;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReminderDAO {
    private static final String FIND_USER_REMINDERS_SQL = "SELECT * FROM Reminders WHERE user_id = ? AND group_id IS NULL";
    private static final String FIND_GROUP_REMINDERS_SQL = "SELECT * FROM Reminders WHERE group_id = ?";
    private static final String FIND_USER_AND_GROUP_REMINDERS_SQL = "SELECT * FROM Reminders WHERE user_id = ? AND group_id = ?";
    private static final String FIND_REMINDER_BY_ID_SQL = "SELECT * FROM Reminders WHERE id = ?";
    private static final String SAVE_SQL = "INSERT INTO Reminders (user_id, group_id, title, message, send_at, status) VALUES (?,?,?,?,?,?)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM Reminders WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE Reminders SET title = ?, message = ?, send_at = ? WHERE id = ?";

    public List<Reminder> findUserReminders(int userId) {
        return getReminders(userId, FIND_USER_REMINDERS_SQL);
    }

    public List<Reminder> findGroupReminders(int groupId) {
        return getReminders(groupId, FIND_GROUP_REMINDERS_SQL);
    }

    private List<Reminder> getReminders(int parameter, String sql) {
        List<Reminder> reminders = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, parameter);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                reminders.add(
                        new Reminder(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getInt("group_id"),
                                resultSet.getString("title"),
                                resultSet.getString("message"),
                                resultSet.getTimestamp("send_at").toLocalDateTime(),
                                resultSet.getString("status")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return reminders;
    }

    public List<Reminder> findUserAndGroupReminders(int userId, int groupId) {
        List<Reminder> reminders = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_AND_GROUP_REMINDERS_SQL)) {
            statement.setInt(1, userId);
            statement.setInt(2, groupId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                reminders.add(
                        new Reminder(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getInt("group_id"),
                                resultSet.getString("title"),
                                resultSet.getString("message"),
                                resultSet.getTimestamp("send_at").toLocalDateTime(),
                                resultSet.getString("status")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return reminders;
    }

    public Reminder findReminderById(int reminderId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_REMINDER_BY_ID_SQL)) {
            statement.setInt(1, reminderId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Reminder(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("group_id"),
                        resultSet.getString("title"),
                        resultSet.getString("message"),
                        resultSet.getTimestamp("send_at").toLocalDateTime(),
                        resultSet.getString("status")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public Reminder save(Reminder reminder) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, reminder.getUserId());
            if (reminder.getGroupId() == 0) {
                statement.setNull(2, java.sql.Types.INTEGER);
            } else {
                statement.setInt(2, reminder.getGroupId());
            }
            statement.setString(3, reminder.getTitle());
            statement.setString(4, reminder.getMessage());
            statement.setTimestamp(5, Timestamp.valueOf(reminder.getSendAt()));
            statement.setString(6, reminder.getStatus());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);

            return new Reminder(
                    id,
                    reminder.getUserId(),
                    reminder.getGroupId(),
                    reminder.getTitle(),
                    reminder.getMessage(),
                    reminder.getSendAt(),
                    reminder.getStatus()
            );
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public void update(Reminder updatedReminder) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, updatedReminder.getTitle());
            statement.setString(2, updatedReminder.getMessage());
            statement.setTimestamp(3, Timestamp.valueOf(updatedReminder.getSendAt()));
            statement.setInt(4, updatedReminder.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public void deleteById(int id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
