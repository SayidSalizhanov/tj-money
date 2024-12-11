package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.exceptions.UserNotFoundException;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.util.PasswordUtil;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
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

    public void update(int userId, User oldUser, String username, String telegramId, boolean sendingToTelegram, boolean sendingToEmail) {
        if (userDAO.findByUsername(username) != null && !oldUser.getUsername().equals(username)) throw new UpdateException("Пользователь с таким именем уже существует");

        if (telegramId.isEmpty()) userDAO.update(new User(userId, username, null, null, null, sendingToTelegram, sendingToEmail));
        else userDAO.update(new User(userId, username, null, null, telegramId, sendingToTelegram, sendingToEmail));
    }

    public void changePassword(String newPassword, int userId) {
        userDAO.updatePassword(PasswordUtil.encrypt(newPassword), userId);
    }

    public void delete(int userId) {
        userDAO.delete(userId);
    }
}
