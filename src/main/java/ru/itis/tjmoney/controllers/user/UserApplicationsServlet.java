package ru.itis.tjmoney.controllers.user;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.interfaces.IApplicationService;

import java.io.IOException;

@WebServlet("/user/applications")
public class UserApplicationsServlet extends HttpServlet {
    private IApplicationService applicationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        applicationService = (IApplicationService) getServletContext().getAttribute("applicationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");

        getUserGroupApplications(userId, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");
        if ("DELETE".equals(method)) {
            deleteUserGroupApplication(
                    Integer.parseInt(req.getParameter("applicationId")),
                    (Integer) req.getSession().getAttribute("userId"),
                    req, resp
            );
        }
    }

    private void getUserGroupApplications(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("applicationsDTOs", applicationService.getUserApplicationGroupDTOs(userId));
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("/templates/users/userApplications.jsp").forward(req, resp);
    }

    private void deleteUserGroupApplication(int applicationId, int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        applicationService.deleteUserApplication(applicationId);
    }
}
