package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.exceptions.LoginException;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.util.PasswordUtil;

public class LoginService {
    private final UserDAO userDAO;

    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User login(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user == null) throw new LoginException("Пользователь с такой почтой не найден");
        if (!user.getPassword().equals(PasswordUtil.encrypt(password))) throw new LoginException("Неправильный пароль");
        return user;
    }
}
