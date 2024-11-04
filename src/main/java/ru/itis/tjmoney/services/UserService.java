package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.exceptions.UserNotFoundException;
import ru.itis.tjmoney.models.User;

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
}
