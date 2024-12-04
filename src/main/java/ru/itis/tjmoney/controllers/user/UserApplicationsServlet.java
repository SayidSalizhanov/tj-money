package ru.itis.tjmoney.controllers.user;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.ApplicationService;
import ru.itis.tjmoney.services.GroupService;
import ru.itis.tjmoney.services.TransactionService;
import ru.itis.tjmoney.services.UserService;

import java.io.IOException;

@WebServlet("/user/applications")
public class UserApplicationsServlet extends HttpServlet {
    private UserService userService;
    private TransactionService transactionService;
    private GroupService groupService;
    private ApplicationService applicationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
        transactionService = (TransactionService) getServletContext().getAttribute("transactionService");
        groupService = (GroupService) getServletContext().getAttribute("groupService");
        applicationService = (ApplicationService) getServletContext().getAttribute("applicationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));

        getUserGroupApplications(userId, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");
        if ("DELETE".equals(method)) {
            deleteUserGroupApplication(
                    Integer.parseInt(req.getParameter("applicationId")),
                    Integer.parseInt(req.getParameter("userId")),
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
        resp.sendRedirect("/user/applications?userId=%d".formatted(userId));
    }
}
