package ru.itis.tjmoney.services.interfaces;

import ru.itis.tjmoney.models.User;

public interface IUserService {
    User getUserById(int id);
    User getByUsername(String username);
    void update(int userId, String username, String telegramId, boolean sendingToTelegram, boolean sendingToEmail);
    void changePassword(String oldPassword, String newPassword, String repeatPassword, int userId);
    void delete(int userId);
    void uploadPhoto(int userId, String url);
    String getPhotoUrl(int userId);
    void deletePhoto(int userId);
}
