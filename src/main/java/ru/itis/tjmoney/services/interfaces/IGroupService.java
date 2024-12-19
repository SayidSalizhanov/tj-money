package ru.itis.tjmoney.services.interfaces;

import ru.itis.tjmoney.dto.GroupDTO;
import ru.itis.tjmoney.dto.UserGroupDTO;
import ru.itis.tjmoney.models.Group;

import java.util.List;

public interface IGroupService {
    List<UserGroupDTO> getUserGroupsDTOs(int userId);
    void save(int userId, String name, String description);
    Group getGroupById(int groupId);
    GroupDTO getGroupDTOById(int groupId);
    List<Group> getAllGroups();
    String getAdminUsername(int groupId);
    List<Group> getGroupsWhereUserNotJoined(int userId);
    void update(int groupId, String name, String description);
    void delete(int groupId);
}
