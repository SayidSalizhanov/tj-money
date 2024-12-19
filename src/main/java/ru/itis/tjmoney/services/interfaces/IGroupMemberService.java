package ru.itis.tjmoney.services.interfaces;

import ru.itis.tjmoney.dto.GroupMemberDTO;
import ru.itis.tjmoney.models.GroupMember;

import java.util.List;

public interface IGroupMemberService {
    List<GroupMember> getMembersByGroupId(int groupId);
    List<GroupMemberDTO> getMembersDTO(int groupId);
    GroupMember getGroupMember(int userId, int groupId);
    void save(int userId, int groupId);
    void delete(int id);
    void deleteByUserIdAndGroupId(int userId, int groupId);
}
