package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.exceptions.RegistrationException;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.util.PasswordUtil;

public class RegistrationService {
    private final UserDAO userDAO;

    public RegistrationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User register(String username, String email, String password, String repeatPassword) {
        if (userDAO.findByEmail(email) != null) {
            throw new RegistrationException("Пользователь с таким email уже существует");
        }
        if (userDAO.findByUsername(username) != null) {
            throw new RegistrationException("Пользователь с таким именем уже существует");
        }
        if (!password.equals(repeatPassword)) {
            throw new RegistrationException("Введенные пароли не совпадают");
        }

        User user = new User(0, username, email, PasswordUtil.encrypt(password), null, false, false);

        return userDAO.save(user);
    }
}
