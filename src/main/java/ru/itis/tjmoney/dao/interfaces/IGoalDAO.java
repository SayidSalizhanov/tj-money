package ru.itis.tjmoney.dao.interfaces;

import ru.itis.tjmoney.models.Goal;

import java.util.List;

public interface IGoalDAO {
    List<Goal> findUserGoals(int userId);
    List<Goal> findGroupGoals(int groupId);
    Goal findGoalById(int goalId);
    Goal save(Goal goal);
    void update(Goal updatedGoal);
    void deleteById(int id);
}
