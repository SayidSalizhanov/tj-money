package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.GroupDAO;
import ru.itis.tjmoney.dao.GroupMemberDAO;
import ru.itis.tjmoney.dto.UserGroupDTO;
import ru.itis.tjmoney.models.Group;
import ru.itis.tjmoney.models.GroupMember;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GroupService {
    private final GroupDAO groupDAO;
    private final GroupMemberDAO groupMemberDAO;

    public GroupService(GroupDAO groupDAO, GroupMemberDAO groupMemberDAO) {
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

    public List<Group> getAllGroups() {
        return groupDAO.findAll();
    }
}
