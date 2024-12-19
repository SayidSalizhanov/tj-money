package ru.itis.tjmoney.services.interfaces;

import ru.itis.tjmoney.dto.ApplicationGroupDTO;
import ru.itis.tjmoney.dto.ApplicationUserDTO;

import java.util.List;

public interface IApplicationService {
    List<ApplicationGroupDTO> getUserApplicationGroupDTOs(int userId);
    List<ApplicationUserDTO> getGroupApplicationUserDTOs(int groupId);
    void deleteUserApplication(int id);
    void createApplication(int userId, int groupId);
    void updateStatus(int applicationId, String applicationStatus);
}
