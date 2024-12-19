package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.interfaces.IGoalDAO;
import ru.itis.tjmoney.models.Goal;
import ru.itis.tjmoney.services.interfaces.IGoalService;

import java.util.List;

public class GoalService implements IGoalService {
    private final IGoalDAO goalDAO;

    public GoalService(IGoalDAO goalDAO) {
        this.goalDAO = goalDAO;
    }

    @Override
    public List<Goal> getUserAndGroupGoals(int userId, int groupId) {
        return groupId == 0 ? goalDAO.findUserGoals(userId) : goalDAO.findGroupGoals(groupId);
    }

    @Override
    public Goal getGoal(int goalId) {
        return goalDAO.findGoalById(goalId);
    }

    @Override
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

    @Override
    public void delete(int id) {
        goalDAO.deleteById(id);
    }

    @Override
    public void update(String title, String description, int progress, int id) {
        goalDAO.update(new Goal(id, 0, 0, title, description, progress));
    }
}
