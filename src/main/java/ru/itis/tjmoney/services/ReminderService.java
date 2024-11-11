package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.ReminderDAO;
import ru.itis.tjmoney.models.Goal;
import ru.itis.tjmoney.models.Reminder;

import java.time.LocalDateTime;
import java.util.List;

public class ReminderService {
    private final ReminderDAO reminderDAO;

    public ReminderService(ReminderDAO reminderDAO) {
        this.reminderDAO = reminderDAO;
    }

    public List<Reminder> getUserReminders(int userId) {
        return reminderDAO.findUserReminders(userId);
    }

    public List<Reminder> getGroupReminders(int groupId) {
        return reminderDAO.findGroupReminders(groupId);
    }

    public List<Reminder> getUserAndGroupReminders(int userId, int groupId) {
        return groupId == 0 ? reminderDAO.findUserReminders(userId) : reminderDAO.findUserAndGroupReminders(userId, groupId);
    }

    public Reminder getReminder(int reminderId) {
        return reminderDAO.findReminderById(reminderId);
    }

    public void save(int userId, int groupId, String title, String message, LocalDateTime sendAt) {
        reminderDAO.save(
                new Reminder(
                        0,
                        userId,
                        groupId,
                        title,
                        message,
                        sendAt,
                        "Создано"
                )
        );
    }

    public void delete(int id) {
        reminderDAO.deleteById(id);
    }

    public void update(String title, String message, LocalDateTime sendAt, int id) {
        reminderDAO.update(
                new Reminder(
                        id,
                        0,
                        0,
                        title,
                        message,
                        sendAt,
                        null
                )
        );

        // здесь должна быть какая-то проверка
    }
}
