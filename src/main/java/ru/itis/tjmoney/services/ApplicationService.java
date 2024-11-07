package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.ApplicationDAO;
import ru.itis.tjmoney.dao.GroupDAO;
import ru.itis.tjmoney.dto.ApplicationDTO;
import ru.itis.tjmoney.models.Application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationService {
    private final ApplicationDAO applicationDAO;
    private final GroupDAO groupDAO;

    public ApplicationService(ApplicationDAO applicationDAO, GroupDAO groupDAO) {
        this.applicationDAO = applicationDAO;
        this.groupDAO = groupDAO;
    }

    public List<ApplicationDTO> getUserApplicationDTOs(int userId) {
        List<Application> applications = applicationDAO.findUserApplications(userId);

        List<ApplicationDTO> applicationsDTOs = new ArrayList<>();
        for (Application application : applications) {
            applicationsDTOs.add(
                    new ApplicationDTO(
                        groupDAO.findById(application.getGroupId()).getName(),
                        application.getSendAt(),
                        application.getStatus()
                    )
            );
        }

        return applicationsDTOs;
    }

    public void deleteUserApplication(int userId) {
        applicationDAO.deleteApplicationByUserId(userId);
    }
}
