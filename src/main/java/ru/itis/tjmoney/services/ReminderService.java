package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.interfaces.IReminderDAO;
import ru.itis.tjmoney.exceptions.ReminderException;
import ru.itis.tjmoney.models.Reminder;
import ru.itis.tjmoney.services.interfaces.IReminderService;

import java.time.LocalDateTime;
import java.util.List;

public class ReminderService implements IReminderService {
    private final IReminderDAO reminderDAO;

    public ReminderService(IReminderDAO reminderDAO) {
        this.reminderDAO = reminderDAO;
    }

    @Override
    public List<Reminder> getUserAndGroupReminders(int userId, int groupId) {
        return groupId == 0 ? reminderDAO.findUserReminders(userId) : reminderDAO.findGroupReminders(groupId);
    }

    @Override
    public Reminder getReminder(int reminderId) {
        return reminderDAO.findReminderById(reminderId);
    }

    @Override
    public void save(int userId, int groupId, String title, String message, LocalDateTime sendAt) {
        if (sendAt.isBefore(LocalDateTime.now())) throw new ReminderException("Дата и время напоминания не могу быть в прошлом");
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

    @Override
    public void delete(int id) {
        reminderDAO.deleteById(id);
    }

    @Override
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
    }
}
