package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.interfaces.IUserDAO;
import ru.itis.tjmoney.exceptions.LoginException;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.services.interfaces.ILoginService;
import ru.itis.tjmoney.util.PasswordUtil;

public class LoginService implements ILoginService {
    private final IUserDAO userDAO;

    public LoginService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User login(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user == null) throw new LoginException("Пользователь с такой почтой не найден");
        if (!user.getPassword().equals(PasswordUtil.encrypt(password))) throw new LoginException("Неправильный пароль");
        return user;
    }
}
