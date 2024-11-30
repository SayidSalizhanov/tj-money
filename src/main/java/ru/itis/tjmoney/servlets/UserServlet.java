package ru.itis.tjmoney.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.services.ApplicationService;
import ru.itis.tjmoney.services.GroupService;
import ru.itis.tjmoney.services.TransactionService;
import ru.itis.tjmoney.services.UserService;

import java.io.IOException;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    private UserService userService;
    private TransactionService transactionService;
    private GroupService groupService;
    private ApplicationService applicationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        transactionService = (TransactionService) getServletContext().getAttribute("transactionService");
        groupService = (GroupService) getServletContext().getAttribute("groupService");
        applicationService = (ApplicationService) getServletContext().getAttribute("applicationService");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String idStr = pathParts[1];
        int userId;
        try {
            userId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
            return;
        }

        String action = (pathParts.length > 2) ? pathParts[2] : null;
        String subAction = (pathParts.length > 3) ? pathParts[3] : null;

        if (action == null) {
            getUserRequest(userId, req, resp);
            return;
        }

        switch (action) {
            case "settings":
                getUserSettings(userId, req, resp);
                break;
            case "groups":
                if (subAction == null) {
                    getUserGroups(userId, req, resp);
                } else if ("applications".equals(subAction)) {
                    getUserGroupApplications(userId, req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");
        if ("DELETE".equals(method)) {
            doDelete(req, resp);
        } else if ("PUT".equals(method)) {
            doPut(req, resp);
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String idStr = pathParts[1];
        int userId;
        try {
            userId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return;
        }

        String action = (pathParts.length > 2) ? pathParts[2] : null;
        String subAction = (pathParts.length > 3) ? pathParts[3] : null;

        if (action == null) {
            return;
        }

        switch (action) {
            case "settings":
                break;
            case "groups":
                if (subAction == null) {
                    getUserGroups(userId, req, resp);
                } else if ("applications".equals(subAction)) {
                    getUserGroupApplications(userId, req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String idStr = pathParts[1];
        int userId;
        try {
            userId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return;
        }

        String action = (pathParts.length > 2) ? pathParts[2] : null;
        String subAction = (pathParts.length > 3) ? pathParts[3] : null;

        if (action == null) {
            return;
        }

        switch (action) {
            case "settings":
                putUserSettings(userId,
                        req.getParameter("username"),
                        req.getParameter("password"),
                        req.getParameter("newPassword"),
                        req.getParameter("repeatPassword"),
                        req.getParameter("telegramId"),
                        Boolean.parseBoolean(req.getParameter("sendingToTelegram")),
                        Boolean.parseBoolean(req.getParameter("sendingToEmail")),
                        req, resp);
                break;
            case "groups":
                if (subAction == null) {
                    getUserGroups(userId, req, resp);
                } else if ("applications".equals(subAction)) {
                    getUserGroupApplications(userId, req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String idStr = pathParts[1];
        int userId;
        try {
            userId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return;
        }

        String action = (pathParts.length > 2) ? pathParts[2] : null;
        String subAction = (pathParts.length > 3) ? pathParts[3] : null;

        if (action == null) {
            return;
        }

        switch (action) {
            case "settings":
                deleteUserSettings(userId, req, resp);
                break;
            case "groups":
                if (subAction == null) {
                    getUserGroups(userId, req, resp);
                } else if ("applications".equals(subAction)) {
                    deleteUserGroupApplication(Integer.parseInt(req.getParameter("applicationId")), userId, req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
    }

    //=====

    private void getUserRequest(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("user", userService.getUserById(userId));
        req.setAttribute("transactions", transactionService.getUserTransactions(userId));
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", 0);
        req.getRequestDispatcher("templates/userProfile.jsp").forward(req, resp);
    }

    //=====

    private void getUserSettings(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("user", userService.getUserById(userId));
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("templates/users/userSettings.jsp").forward(req, resp);
    }

    private void putUserSettings(int userId, String username, String password, String newPassword, String repeatPassword, String telegramId, boolean sendingToTelegram, boolean sendingToEmail, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            userService.update(userId, username, password, newPassword, repeatPassword, telegramId, sendingToTelegram, sendingToEmail);
            resp.sendRedirect(req.getContextPath() + "/users/" + userId);
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("templates/users/settings.jsp").forward(req, resp);
        }
    }

    private void deleteUserSettings(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userService.delete(userId);
        resp.sendRedirect(req.getContextPath() + "/mainPage");
    }

    //=====

    private void getUserGroups(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("userGroupsDTOs", groupService.getUserGroupsDTOs(userId));
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("templates/users/userSettings.jsp").forward(req, resp);
    }

    //=====

    private void getUserGroupApplications(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("applicationsDTOs", applicationService.getUserApplicationGroupDTOs(userId));
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("templates/users/userApplications.jsp").forward(req, resp);
    }

    private void deleteUserGroupApplication(int applicationId, int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        applicationService.deleteUserApplication(applicationId);
        resp.sendRedirect(req.getContextPath() + "/users/" + userId + "/groups/applications");
    }
}