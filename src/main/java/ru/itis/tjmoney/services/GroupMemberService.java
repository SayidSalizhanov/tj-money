package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.GroupMemberDAO;
import ru.itis.tjmoney.exceptions.GroupMemberNotFoundException;
import ru.itis.tjmoney.models.GroupMember;

import java.util.List;

public class GroupMemberService {
    private final GroupMemberDAO groupMemberDAO;

    public GroupMemberService(GroupMemberDAO groupMemberDAO) {
        this.groupMemberDAO = groupMemberDAO;
    }

    public List<GroupMember> getMembersByGroupId(int groupId) {
        return groupMemberDAO.findByGroupId(groupId);
    }

    public GroupMember getGroupMember(int userId, int groupId){
        GroupMember groupMember = groupMemberDAO.findByUserIdAndGroupId(userId, groupId);
        if (groupMember == null) {
            throw new GroupMemberNotFoundException("Участник группы с таким userId и groupId не найден");
        }
        return groupMember;
    }
}
