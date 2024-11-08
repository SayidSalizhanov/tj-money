package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.GoalDAO;
import ru.itis.tjmoney.models.Goal;
import ru.itis.tjmoney.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class GoalService {
    private final GoalDAO goalDAO;

    public GoalService(GoalDAO goalDAO) {
        this.goalDAO = goalDAO;
    }

    public List<Goal> getUserGoals(int userId) {
        return goalDAO.findUserGoals(userId);
    }

    public List<Goal> getGroupGoals(int groupId) {
        return goalDAO.findGroupGoals(groupId);
    }

    public List<Goal> getUserAndGroupGoals(int userId, int groupId) {
        return groupId == 0 ? goalDAO.findUserGoals(userId) : goalDAO.findUserAndGroupGoals(userId, groupId);
    }

    public Goal getGoal(int goalId) {
        return goalDAO.findGoalById(goalId);
    }

    public void save(int userId, int groupId, String title, String description, int progress) {
        goalDAO.save(new Goal(
                0,
                userId,
                groupId,
                title,
                description,
                progress
        ));
    }

    public void delete(int id) {
        goalDAO.deleteById(id);
    }

    public void update(String title, String description, int progress, int id) {
        goalDAO.update(new Goal(id, 0, 0, title, description, progress));

        // здесь должна быть какая-то проверка
    }
}
