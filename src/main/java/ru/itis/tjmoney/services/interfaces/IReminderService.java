package ru.itis.tjmoney.services.interfaces;

import ru.itis.tjmoney.models.Reminder;

import java.time.LocalDateTime;
import java.util.List;

public interface IReminderService {
    List<Reminder> getUserAndGroupReminders(int userId, int groupId);
    Reminder getReminder(int reminderId);
    void save(int userId, int groupId, String title, String message, LocalDateTime sendAt);
    void delete(int id);
    void update(String title, String message, LocalDateTime sendAt, int id);
}
