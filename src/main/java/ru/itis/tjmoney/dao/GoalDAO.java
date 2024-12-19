package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.dao.interfaces.IGoalDAO;
import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.Goal;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalDAO implements IGoalDAO {
    private final Connection connection = ConnectionManager.getConnection();

    private static final String FIND_USER_GOALS_SQL = "SELECT * FROM Goals WHERE user_id = ? AND group_id IS NULL";
    private static final String FIND_GROUP_GOALS_SQL = "SELECT * FROM Goals WHERE group_id = ?";
    private static final String FIND_GOAL_BY_ID_SQL = "SELECT * FROM Goals WHERE id = ?";
    private static final String SAVE_SQL = "INSERT INTO Goals (user_id, group_id, title, description, progress) VALUES (?,?,?,?,?)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM Goals WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE Goals SET title = ?, description = ?, progress = ? WHERE id = ?";

    @Override
    public List<Goal> findUserGoals(int userId) {
        return getGoals(userId, FIND_USER_GOALS_SQL);
    }

    @Override
    public List<Goal> findGroupGoals(int groupId) {
        return getGoals(groupId, FIND_GROUP_GOALS_SQL);
    }

    private List<Goal> getGoals(int parameter, String sql) {
        List<Goal> goals = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, parameter);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                goals.add(
                        new Goal(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getInt("group_id"),
                                resultSet.getString("title"),
                                resultSet.getString("description"),
                                resultSet.getInt("progress")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return goals;
    }

    @Override
    public Goal findGoalById(int goalId) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_GOAL_BY_ID_SQL)) {
            statement.setInt(1, goalId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Goal(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("group_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("progress")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Goal save(Goal goal) {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, goal.getUserId());
            if (goal.getGroupId() == 0) {
                statement.setNull(2, java.sql.Types.INTEGER);
            } else {
                statement.setInt(2, goal.getGroupId());
            }
            statement.setString(3, goal.getTitle());
            statement.setString(4, goal.getDescription());
            statement.setInt(5, goal.getProgress());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);

            return new Goal(
                    id,
                    goal.getUserId(),
                    goal.getGroupId(),
                    goal.getTitle(),
                    goal.getDescription(),
                    goal.getProgress()
            );
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void update(Goal updatedGoal) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, updatedGoal.getTitle());
            statement.setString(2, updatedGoal.getDescription());
            statement.setInt(3, updatedGoal.getProgress());
            statement.setInt(4, updatedGoal.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
