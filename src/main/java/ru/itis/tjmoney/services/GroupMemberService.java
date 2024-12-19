package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.interfaces.IApplicationDAO;
import ru.itis.tjmoney.dao.interfaces.IGroupMemberDAO;
import ru.itis.tjmoney.dao.interfaces.IUserDAO;
import ru.itis.tjmoney.dto.GroupMemberDTO;
import ru.itis.tjmoney.exceptions.GroupMemberNotFoundException;
import ru.itis.tjmoney.models.GroupMember;
import ru.itis.tjmoney.services.interfaces.IGroupMemberService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GroupMemberService implements IGroupMemberService {
    private final IGroupMemberDAO groupMemberDAO;
    private final IApplicationDAO applicationDAO;
    private final IUserDAO userDAO;

    public GroupMemberService(IGroupMemberDAO groupMemberDAO, IApplicationDAO applicationDAO, IUserDAO userDAO) {
        this.groupMemberDAO = groupMemberDAO;
        this.applicationDAO = applicationDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<GroupMember> getMembersByGroupId(int groupId) {
        return groupMemberDAO.findByGroupId(groupId);
    }

    @Override
    public List<GroupMemberDTO> getMembersDTO(int groupId) {
        return getMembersByGroupId(groupId).stream()
                .map(m -> new GroupMemberDTO(
                        userDAO.findById(m.getUserId()).getUsername(),
                        m.getJoinedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        m.getRole()
                ))
                .toList();
    }

    @Override
    public GroupMember getGroupMember(int userId, int groupId) {
        GroupMember groupMember = groupMemberDAO.findByUserIdAndGroupId(userId, groupId);
        if (groupMember == null) {
            throw new GroupMemberNotFoundException("Участник группы с таким userId и groupId не найден");
        }
        return groupMember;
    }

    @Override
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

    @Override
    public void delete(int id) {
        groupMemberDAO.delete(id);
    }

    @Override
    public void deleteByUserIdAndGroupId(int userId, int groupId) {
        groupMemberDAO.deleteByUserIdAndGroupId(userId, groupId);
        applicationDAO.deleteByUserIdAndGroupId(userId, groupId);
    }
}
