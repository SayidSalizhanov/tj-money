package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.Goal;
import ru.itis.tjmoney.models.Record;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordDAO {
    private static final String FIND_USER_RECORDS_SQL = "SELECT * FROM Records WHERE user_id = ? AND group_id = null";
    private static final String FIND_GROUP_RECORDS_SQL = "SELECT * FROM Records WHERE user_id = null AND group_id = ?";
    private static final String FIND_USER_AND_GROUP_RECORDS_SQL = "SELECT * FROM Records WHERE user_id = ? AND group_id = ?";
    private static final String FIND_RECORD_BY_ID_SQL = "SELECT * FROM Records WHERE id = ?";
    private static final String SAVE_SQL = "INSERT INTO Records (user_id, group_id, title, content, created_at, updated_at) VALUES (?,?,?,?,?,?)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM Records WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE Records SET title = ?, content = ?, updated_at = ? WHERE id = ?";

    public List<Record> findUserRecords(int userId) {
        return getRecords(userId, FIND_USER_RECORDS_SQL);
    }

    public List<Record> findGroupRecords(int groupId) {
        return getRecords(groupId, FIND_GROUP_RECORDS_SQL);
    }

    private List<Record> getRecords(int parameter, String sql) {
        List<Record> records = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, parameter);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                records.add(
                        new Record(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getInt("group_id"),
                                resultSet.getString("title"),
                                resultSet.getString("content"),
                                resultSet.getTimestamp("created_at").toLocalDateTime(),
                                resultSet.getTimestamp("updated_at").toLocalDateTime()
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return records;
    }

    public List<Record> findUserAndGroupRecords(int userId, int groupId) {
        List<Record> records = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_AND_GROUP_RECORDS_SQL)) {
            statement.setInt(1, userId);
            statement.setInt(2, groupId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                records.add(
                        new Record(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getInt("group_id"),
                                resultSet.getString("title"),
                                resultSet.getString("content"),
                                resultSet.getTimestamp("created_at").toLocalDateTime(),
                                resultSet.getTimestamp("updated_at").toLocalDateTime()
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return records;
    }

    public Record findRecordById(int recordId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_RECORD_BY_ID_SQL)) {
            statement.setInt(1, recordId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Record(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("group_id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public Record save(Record record) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, record.getUserId());
            statement.setInt(2, record.getGroupId());
            statement.setString(3, record.getTitle());
            statement.setString(4, record.getContent());
            statement.setTimestamp(5, Timestamp.valueOf(record.getCreatedAt()));
            statement.setTimestamp(6, Timestamp.valueOf(record.getUpdatedAt()));

            statement.executeUpdate();

            int id = statement.getGeneratedKeys().getInt(1);

            return new Record(
                    id,
                    record.getUserId(),
                    record.getGroupId(),
                    record.getTitle(),
                    record.getContent(),
                    record.getCreatedAt(),
                    record.getUpdatedAt()
            );
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public void update(Record updatedRecord) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, updatedRecord.getTitle());
            statement.setString(2, updatedRecord.getContent());
            statement.setTimestamp(3, Timestamp.valueOf(updatedRecord.getUpdatedAt()));
            statement.setInt(4, updatedRecord.getId());

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
