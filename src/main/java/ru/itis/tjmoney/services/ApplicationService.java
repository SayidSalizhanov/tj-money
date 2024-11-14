package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.ApplicationDAO;
import ru.itis.tjmoney.dao.GroupDAO;
import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.dto.ApplicationGroupDTO;
import ru.itis.tjmoney.dto.ApplicationUserDTO;
import ru.itis.tjmoney.models.Application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApplicationService {
    private final ApplicationDAO applicationDAO;
    private final GroupDAO groupDAO;
    private final UserDAO userDAO;

    public ApplicationService(ApplicationDAO applicationDAO, GroupDAO groupDAO, UserDAO userDAO) {
        this.applicationDAO = applicationDAO;
        this.groupDAO = groupDAO;
        this.userDAO = userDAO;
    }

    public List<ApplicationGroupDTO> getUserApplicationGroupDTOs(int userId) {
        List<Application> applications = applicationDAO.findUserApplications(userId);

        List<ApplicationGroupDTO> applicationsDTOs = new ArrayList<>();
        for (Application application : applications) {
            applicationsDTOs.add(
                    new ApplicationGroupDTO(
                        groupDAO.findById(application.getGroupId()).getName(),
                        application.getSendAt(),
                        application.getStatus()
                    )
            );
        }

        return applicationsDTOs;
    }

    public List<ApplicationUserDTO> getGroupApplicationUserDTOs(int groupId) {
        List<Application> applications = applicationDAO.findGroupApplications(groupId).stream()
                                                                                      .filter(a -> a.getStatus().equalsIgnoreCase("в ожидании"))
                                                                                      .toList();

        List<ApplicationUserDTO> applicationsDTOs = new ArrayList<>();
        for (Application application : applications) {
            applicationsDTOs.add(
                    new ApplicationUserDTO(
                            userDAO.findById(application.getUserId()).getUsername(),
                            application.getSendAt()
                    )
            );
        }

        return applicationsDTOs;
    }

    public void deleteUserApplication(int userId) {
        applicationDAO.deleteApplicationByUserId(userId);
    }

    public void createApplication(int userId, int groupId) {
        applicationDAO.save(
                new Application(
                        0,
                        userId,
                        groupId,
                        LocalDateTime.now(),
                        "В ожидании"
                )
        );
    }

    public void updateStatus(int applicationId, String applicationStatus) {
        applicationDAO.update(
                new Application(
                        applicationId,
                        0,
                        0,
                        null,
                        applicationStatus
                )
        );
    }
}
