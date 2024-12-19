package ru.itis.tjmoney.dao.interfaces;

import ru.itis.tjmoney.models.Reminder;

import java.util.List;

public interface IReminderDAO {
    List<Reminder> findUserReminders(int userId);
    List<Reminder> findGroupReminders(int groupId);
    Reminder findReminderById(int reminderId);
    Reminder save(Reminder reminder);
    void update(Reminder updatedReminder);
    void deleteById(int id);
}
