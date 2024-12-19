package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.ApplicationDAO;
import ru.itis.tjmoney.dao.GroupDAO;
import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.dto.ApplicationGroupDTO;
import ru.itis.tjmoney.dto.ApplicationUserDTO;
import ru.itis.tjmoney.models.Application;
import ru.itis.tjmoney.services.interfaces.IApplicationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ApplicationService implements IApplicationService {
    private final ApplicationDAO applicationDAO;
    private final GroupDAO groupDAO;
    private final UserDAO userDAO;

    public ApplicationService(ApplicationDAO applicationDAO, GroupDAO groupDAO, UserDAO userDAO) {
        this.applicationDAO = applicationDAO;
        this.groupDAO = groupDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<ApplicationGroupDTO> getUserApplicationGroupDTOs(int userId) {
        List<Application> applications = applicationDAO.findUserApplications(userId);

        List<ApplicationGroupDTO> applicationsDTOs = new ArrayList<>();
        for (Application application : applications) {
            applicationsDTOs.add(
                    new ApplicationGroupDTO(
                            application.getId(),
                            groupDAO.findById(application.getGroupId()).getName(),
                            application.getSendAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                            application.getStatus()
                    )
            );
        }

        return applicationsDTOs;
    }

    @Override
    public List<ApplicationUserDTO> getGroupApplicationUserDTOs(int groupId) {
        List<Application> applications = applicationDAO.findGroupApplications(groupId).stream()
                                                                                      .filter(a -> a.getStatus().equalsIgnoreCase("В ожидании"))
                                                                                      .toList();

        List<ApplicationUserDTO> applicationsDTOs = new ArrayList<>();
        for (Application application : applications) {
            applicationsDTOs.add(
                    new ApplicationUserDTO(
                            String.valueOf(application.getId()),
                            userDAO.findById(application.getUserId()).getUsername(),
                            application.getSendAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    )
            );
        }

        return applicationsDTOs;
    }

    @Override
    public void deleteUserApplication(int id) {
        applicationDAO.deleteApplicationById(id);
    }

    @Override
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

    @Override
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
