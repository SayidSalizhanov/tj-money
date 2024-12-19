package ru.itis.tjmoney.services.interfaces;

import ru.itis.tjmoney.models.User;

public interface ILoginService {
    User login(String email, String password);
}
