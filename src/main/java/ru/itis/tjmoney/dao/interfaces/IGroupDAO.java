package ru.itis.tjmoney.dao.interfaces;

import ru.itis.tjmoney.models.Group;

import java.util.List;

public interface IGroupDAO {
    void update(Group updatedGroup);
    List<Group> findAll();
    Group findByName(String name);
    Group findById(int groupId);
    Group save(Group group);
    void delete(int groupId);
}
