package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.GroupMember;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class GroupMemberDAO {
    private static final String SAVE_SQL = "INSERT INTO Group_Members (user_id, group_id, joined_at, role) VALUES (?, ?, ?, ?)";
    private static final String FIND_GROUP_MEMBERS_BY_USER_ID = "SELECT * FROM Group_Members WHERE user_id = ?";

    public GroupMember save(GroupMember groupMember) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, groupMember.getUserId());
            statement.setInt(2, groupMember.getGroupId());
            statement.setTimestamp(3, Timestamp.valueOf(groupMember.getJoinedAt()));
            statement.setString(4, groupMember.getRole());

            statement.executeUpdate();

            int id = statement.getGeneratedKeys().getInt(1);

            return new GroupMember(id, groupMember.getUserId(), groupMember.getGroupId(), groupMember.getJoinedAt(), groupMember.getRole());
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public List<GroupMember> findByUserId(int userId) {
        List<GroupMember> groupMembers = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_GROUP_MEMBERS_BY_USER_ID)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                groupMembers.add(
                        new GroupMember(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getInt("group_id"),
                                resultSet.getTimestamp("joined_at").toLocalDateTime(),
                                resultSet.getString("role")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
