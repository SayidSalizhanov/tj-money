package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.interfaces.IApplicationDAO;
import ru.itis.tjmoney.dao.interfaces.IGroupDAO;
import ru.itis.tjmoney.dao.interfaces.IGroupMemberDAO;
import ru.itis.tjmoney.dao.interfaces.IUserDAO;
import ru.itis.tjmoney.dto.GroupDTO;
import ru.itis.tjmoney.dto.UserGroupDTO;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.models.Application;
import ru.itis.tjmoney.models.Group;
import ru.itis.tjmoney.models.GroupMember;
import ru.itis.tjmoney.services.interfaces.IGroupService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GroupService implements IGroupService {
    private final IUserDAO userDAO;
    private final IGroupDAO groupDAO;
    private final IGroupMemberDAO groupMemberDAO;
    private final IApplicationDAO applicationDAO;

    public GroupService(IUserDAO userDAO, IGroupDAO groupDAO, IGroupMemberDAO groupMemberDAO, IApplicationDAO applicationDAO) {
        this.userDAO = userDAO;
        this.groupDAO = groupDAO;
        this.groupMemberDAO = groupMemberDAO;
        this.applicationDAO = applicationDAO;
    }

    @Override
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

    @Override
    public void save(int userId, String name, String description) {
        Group group = groupDAO.save(new Group(0, name, LocalDateTime.now(), description));
        groupMemberDAO.save(new GroupMember(0, userId, group.getId(), LocalDateTime.now(), "ADMIN"));
    }

    @Override
    public Group getGroupById(int groupId) {
        return groupDAO.findById(groupId);
    }

    @Override
    public GroupDTO getGroupDTOById(int groupId) {
        Group group = getGroupById(groupId);

        return new GroupDTO(
                group.getId(),
                group.getName(),
                group.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                group.getDescription()
        );
    }

    @Override
    public List<Group> getAllGroups() {
        return groupDAO.findAll();
    }

    @Override
    public String getAdminUsername(int groupId) {
        List<GroupMember> groupMembers = groupMemberDAO.findByGroupId(groupId);
        for (GroupMember groupMember : groupMembers) {
            if (groupMember.getRole().equalsIgnoreCase("admin")) {
                return userDAO.findById(groupMember.getUserId()).getUsername();
            }
        }
        return null;
    }

    @Override
    public List<Group> getGroupsWhereUserNotJoined(int userId) {
        List<GroupMember> groupMembers = groupMemberDAO.findByUserId(userId);
        List<Group> userGroups = groupMembers.stream()
                .map(gm -> groupDAO.findById(gm.getGroupId()))
                .toList();
        List<Integer> nonRejectedUserApplicationsGroupId = applicationDAO.findUserApplications(userId).stream()
                .filter(a -> !a.getStatus().equalsIgnoreCase("Отклонено"))
                .map(Application::getGroupId)
                .toList();
        return getAllGroups().stream()
                .filter(g -> !userGroups.contains(g))
                .filter(g -> !nonRejectedUserApplicationsGroupId.contains(g.getId()))
                .toList();
    }

    @Override
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

    @Override
    public void delete(int groupId) {
        groupDAO.delete(groupId);
    }
}
