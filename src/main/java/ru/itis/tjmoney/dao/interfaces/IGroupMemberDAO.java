package ru.itis.tjmoney.dao.interfaces;

import ru.itis.tjmoney.models.GroupMember;

import java.util.List;

public interface IGroupMemberDAO {
    void delete(int id);
    void deleteByUserIdAndGroupId(int userId, int groupId);
    GroupMember findByUserIdAndGroupId(int userId, int groupId);
    GroupMember save(GroupMember groupMember);
    List<GroupMember> findByUserId(int userId);
    List<GroupMember> findByGroupId(int groupId);
    List<GroupMember> findByUserIdWhereAdmin(int userId);
}
