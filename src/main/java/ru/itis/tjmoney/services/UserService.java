package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.AvatarDAO;
import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.exceptions.UserNotFoundException;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.util.PasswordUtil;

public class UserService {
    private final UserDAO userDAO;
    private final AvatarDAO avatarDAO;

    public UserService(UserDAO userDAO, AvatarDAO avatarDAO) {
        this.userDAO = userDAO;
        this.avatarDAO = avatarDAO;
    }

    public User getUserById(int id) {
        User user = userDAO.findById(id);
        if (user == null) throw new UserNotFoundException("Пользователь с таким id не найден");
        return user;
    }

    public User getByUsername(String username) {
        User user = userDAO.findByUsername(username);
        if (user == null) throw new UserNotFoundException("Пользователь с таким именем не найден");
        return user;
    }

    public void update(int userId, String username, String telegramId, boolean sendingToTelegram, boolean sendingToEmail) {
        User oldUser = getUserById(userId);
        if (userDAO.findByUsername(username) != null && !oldUser.getUsername().equals(username)) throw new UpdateException("Пользователь с таким именем уже существует");

        if (telegramId.isEmpty()) userDAO.update(new User(userId, username, null, null, null, sendingToTelegram, sendingToEmail));
        else userDAO.update(new User(userId, username, null, null, telegramId, sendingToTelegram, sendingToEmail));
    }

    public void changePassword(String oldPassword, String newPassword, String repeatPassword, int userId) {
        String currentPassword = getUserById(userId).getPassword();

        if (!PasswordUtil.encrypt(oldPassword).equalsIgnoreCase(currentPassword)) throw new UpdateException("Неверный старый пароль");
        if (!newPassword.equals(repeatPassword)) throw new UpdateException("Новые пароли не совпадают");

        userDAO.updatePassword(PasswordUtil.encrypt(newPassword), userId);
    }

    public void delete(int userId) {
        userDAO.delete(userId);
    }

    public void uploadPhoto(int userId, String url) {
        if (avatarDAO.findUrl(userId) == null) avatarDAO.save(userId, url);
        else avatarDAO.update(userId, url);
    }

    public String getPhotoUrl(int userId) {
        String url = avatarDAO.findUrl(userId);
        if (url == null) return "/static/defaultAvatar.png";
        return url;
    }

    public void deletePhoto(int userId) {
        avatarDAO.delete(userId);
    }
}
