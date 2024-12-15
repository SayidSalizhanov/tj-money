package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.ApplicationDAO;
import ru.itis.tjmoney.dao.GroupMemberDAO;
import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.dto.GroupMemberDTO;
import ru.itis.tjmoney.exceptions.GroupMemberNotFoundException;
import ru.itis.tjmoney.models.GroupMember;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GroupMemberService {
    private final GroupMemberDAO groupMemberDAO;
    private final ApplicationDAO applicationDAO;
    private final UserDAO userDAO;

    public GroupMemberService(GroupMemberDAO groupMemberDAO, ApplicationDAO applicationDAO, UserDAO userDAO) {
        this.groupMemberDAO = groupMemberDAO;
        this.applicationDAO = applicationDAO;
        this.userDAO = userDAO;
    }

    public GroupMember getByUsernameAndGroupId(String username, int groupId) {
        return groupMemberDAO.findByUserIdAndGroupId(userDAO.findByUsername(username).getId(), groupId);
    }

    public List<GroupMember> getMembersByGroupId(int groupId) {
        return groupMemberDAO.findByGroupId(groupId);
    }

    public List<GroupMemberDTO> getMembersDTO(int groupId) {
        return getMembersByGroupId(groupId).stream()
                .map(m -> new GroupMemberDTO(
                        userDAO.findById(m.getUserId()).getUsername(),
                        m.getJoinedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        m.getRole()
                ))
                .toList();
    }

    public GroupMember getGroupMember(int userId, int groupId){
        GroupMember groupMember = groupMemberDAO.findByUserIdAndGroupId(userId, groupId);
        if (groupMember == null) {
            throw new GroupMemberNotFoundException("Участник группы с таким userId и groupId не найден");
        }
        return groupMember;
    }

    public void save(int userId, int groupId) {
        groupMemberDAO.save(
                new GroupMember(
                        0,
                        userId,
                        groupId,
                        LocalDateTime.now(),
                        "USER"
                )
        );
    }

    public void delete(int id) {
        groupMemberDAO.delete(id);
    }

    public void deleteByUserIdAndGroupId(int userId, int groupId) {
        groupMemberDAO.deleteByUserIdAndGroupId(userId, groupId);
        applicationDAO.deleteByUserIdAndGroupId(userId, groupId);
    }
}
