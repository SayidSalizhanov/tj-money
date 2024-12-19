package ru.itis.tjmoney.controllers.user;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.services.interfaces.IUserService;

import java.io.IOException;

@WebServlet("/user/settings")
public class UserSettingsServlet extends HttpServlet {
    private IUserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (IUserService) getServletContext().getAttribute("userService");
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
                    req.getParameter("telegramId"),
                    req.getParameter("sendingToTelegram") != null,
                    req.getParameter("sendingToEmail") != null,
                    req, resp
            );
        }
    }

    private void putUserSettings(int userId, String username, String telegramId, boolean sendingToTelegram, boolean sendingToEmail, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            userService.update(userId, username, telegramId, sendingToTelegram, sendingToEmail);
            resp.sendRedirect("/user");
            req.getSession().setAttribute("username", username);
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("user", userService.getUserById(userId));
            req.setAttribute("userId", userId);
            req.getRequestDispatcher("/templates/users/settings.jsp").forward(req, resp);
        }
    }

    private void deleteUserSettings(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userService.delete(userId);
        req.getSession().invalidate();
        resp.sendRedirect("/mainPage");
    }
}
