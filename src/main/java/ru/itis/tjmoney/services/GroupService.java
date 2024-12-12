package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.GroupDAO;
import ru.itis.tjmoney.dao.GroupMemberDAO;
import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.dto.UserGroupDTO;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.models.Group;
import ru.itis.tjmoney.models.GroupMember;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GroupService {
    private final UserDAO userDAO;
    private final GroupDAO groupDAO;
    private final GroupMemberDAO groupMemberDAO;

    public GroupService(UserDAO userDAO, GroupDAO groupDAO, GroupMemberDAO groupMemberDAO) {
        this.userDAO = userDAO;
        this.groupDAO = groupDAO;
        this.groupMemberDAO = groupMemberDAO;
    }

    public List<UserGroupDTO> getUserGroupsDTOs(int userId) {
        List<GroupMember> groupMembers = groupMemberDAO.findByUserId(userId);
        List<UserGroupDTO> userGroupDTOS = new ArrayList<>();

        for (GroupMember groupMember : groupMembers) {
            Group group = groupDAO.findById(groupMember.getGroupId());
            userGroupDTOS.add(
                    new UserGroupDTO(
                            group.getId(),
                            group.getName(),
                            group.getDescription(),
                            groupMember.getRole()
                    )
            );
        }

        return userGroupDTOS;
    }

    public void save(int userId, String name, String description) {
        Group group = groupDAO.save(new Group(0, name, LocalDateTime.now(), description));
        groupMemberDAO.save(new GroupMember(0, userId, group.getId(), LocalDateTime.now(), "ADMIN"));
    }

    public Group getGroupById(int groupId) {
        return groupDAO.findById(groupId);
    }

    public Group getGroupByName(String name) {
        return groupDAO.findByName(name);
    }

    public List<Group> getAllGroups() {
        return groupDAO.findAll();
    }

    public String getAdminUsername(int groupId) {
        List<GroupMember> groupMembers = groupMemberDAO.findByGroupId(groupId);
        for (GroupMember groupMember : groupMembers) {
            if (groupMember.getRole().equalsIgnoreCase("admin")) {
                return userDAO.findById(groupMember.getUserId()).getUsername();
            }
        }
        return null;
    }

    public List<Group> getGroupsWhereUserNotJoined(int userId) {
        List<GroupMember> groupMembers = groupMemberDAO.findByUserId(userId);
        List<Group> userGroups = groupMembers.stream().map(gm -> groupDAO.findById(gm.getGroupId())).toList();
        return getAllGroups().stream().filter(g -> !userGroups.contains(g)).toList();
    }

    public void update(int groupId, String name, String description) {
        Group oldGroup = getGroupById(groupId);
        if (groupDAO.findByName(name) != null && !oldGroup.getName().equals(name)) throw new UpdateException("Группа с таким именем уже существует");

        groupDAO.update(
                new Group(
                        groupId,
                        name,
                        null,
                        description
                )
        );
    }

    public void delete(int groupId) {
        groupDAO.delete(groupId);
    }
}
