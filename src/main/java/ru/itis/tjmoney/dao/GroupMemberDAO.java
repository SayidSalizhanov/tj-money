package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.GroupMember;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupMemberDAO {
    private static final String SAVE_SQL = "INSERT INTO Group_Members (user_id, group_id, joined_at, role) VALUES (?, ?, ?, ?)";
    private static final String FIND_GROUP_MEMBERS_BY_USER_ID_SQL = "SELECT * FROM Group_Members WHERE user_id = ?";
    private static final String FIND_GROUP_MEMBERS_BY_GROUP_ID_SQL = "SELECT * FROM Group_Members WHERE group_id = ?";
    private static final String FIND_GROUP_MEMBER_BY_USER_ID_AND_BY_GROUP_ID = "SELECT * FROM Group_Members WHERE user_id = ? AND group_id = ?";

    public GroupMember findByUserIdAndGroupId(int userId, int groupId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_GROUP_MEMBER_BY_USER_ID_AND_BY_GROUP_ID)) {
            statement.setInt(1, userId);
            statement.setInt(2, groupId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new GroupMember(
                        resultSet.getInt("id"),
                        userId,
                        groupId,
                        resultSet.getTimestamp("joined_at").toLocalDateTime(),
                        resultSet.getString("role")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

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
             PreparedStatement statement = connection.prepareStatement(FIND_GROUP_MEMBERS_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                groupMembers.add(
                        new GroupMember(
                                resultSet.getInt("id"),
                                userId,
                                resultSet.getInt("group_id"),
                                resultSet.getTimestamp("joined_at").toLocalDateTime(),
                                resultSet.getString("role")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return groupMembers;
    }

    public List<GroupMember> findByGroupId(int groupId) {
        List<GroupMember> groupMembers = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_GROUP_MEMBERS_BY_GROUP_ID_SQL)) {
            statement.setInt(1, groupId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                groupMembers.add(
                        new GroupMember(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                groupId,
                                resultSet.getTimestamp("joined_at").toLocalDateTime(),
                                resultSet.getString("role")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return groupMembers;
    }
}
