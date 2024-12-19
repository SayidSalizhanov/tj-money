package ru.itis.tjmoney.services.interfaces;

import ru.itis.tjmoney.models.User;

public interface IRegistrationService {
    User register(String username, String email, String password, String repeatPassword);
}
