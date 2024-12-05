package ru.itis.tjmoney.controllers.user;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.services.ApplicationService;
import ru.itis.tjmoney.services.GroupService;
import ru.itis.tjmoney.services.TransactionService;
import ru.itis.tjmoney.services.UserService;

import java.io.IOException;

@WebServlet("/user/settings")
public class UserSettingsServlet extends HttpServlet {
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
        int userId = (Integer) req.getSession().getAttribute("userId");

        req.setAttribute("user", userService.getUserById(userId));
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("/templates/users/settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");
        if ("DELETE".equals(method)) {
            deleteUserSettings(
                    (Integer) req.getSession().getAttribute("userId"),
                    req, resp
            );
        } else if ("PUT".equals(method)) {
            putUserSettings(
                    (Integer) req.getSession().getAttribute("userId"),
                    req.getParameter("username"),
                    req.getParameter("newPassword"),
                    req.getParameter("repeatPassword"),
                    req.getParameter("telegramId"),
                    Boolean.parseBoolean(req.getParameter("sendingToTelegram")),
                    Boolean.parseBoolean(req.getParameter("sendingToEmail")),
                    req, resp
            );
        }
    }

    private void putUserSettings(int userId, String username, String newPassword, String repeatPassword, String telegramId, boolean sendingToTelegram, boolean sendingToEmail, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            User user = userService.getUserById(userId);
            userService.update(userId, user, username, newPassword, repeatPassword, telegramId, sendingToTelegram, sendingToEmail);
            resp.sendRedirect("/user");
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/templates/users/settings.jsp").forward(req, resp);
        }
    }

    private void deleteUserSettings(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userService.delete(userId);
        resp.sendRedirect("/mainPage");
    }
}
