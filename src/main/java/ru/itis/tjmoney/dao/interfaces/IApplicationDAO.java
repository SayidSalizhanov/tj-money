package ru.itis.tjmoney.dao.interfaces;

import ru.itis.tjmoney.models.Application;

import java.util.List;

public interface IApplicationDAO {
    void update(Application application);
    List<Application> findUserApplications(int userId);
    List<Application> findGroupApplications(int groupId);
    void deleteByUserIdAndGroupId(int userId, int groupId);
    void deleteApplicationById(int id);
    void save(Application application);
}
