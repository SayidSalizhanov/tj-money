package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.interfaces.IUserDAO;
import ru.itis.tjmoney.exceptions.RegistrationException;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.services.interfaces.IRegistrationService;
import ru.itis.tjmoney.util.PasswordUtil;

public class RegistrationService implements IRegistrationService {
    private final IUserDAO userDAO;

    public RegistrationService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
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
