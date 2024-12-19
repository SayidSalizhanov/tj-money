package ru.itis.tjmoney.services.interfaces;

import ru.itis.tjmoney.models.Goal;

import java.util.List;

public interface IGoalService {
    List<Goal> getUserAndGroupGoals(int userId, int groupId);
    Goal getGoal(int goalId);
    void save(int userId, int groupId, String title, String description, int progress);
    void delete(int id);
    void update(String title, String description, int progress, int id);
}
