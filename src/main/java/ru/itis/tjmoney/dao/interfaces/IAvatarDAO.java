package ru.itis.tjmoney.dao.interfaces;

public interface IAvatarDAO {
    String findUrl(int userId);
    void save(int userId, String url);
    void update(int userId, String url);
    void delete(int userId);
}
