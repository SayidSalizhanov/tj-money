package ru.itis.tjmoney.dao.interfaces;

import ru.itis.tjmoney.models.User;

public interface IUserDAO {
    User findById(int id);
    User findByEmail(String email);
    User findByUsername(String username);
    User save(User user);
    void update(User updatedUser);
    void updatePassword(String newPassword, int userId);
    void delete(int id);
}
