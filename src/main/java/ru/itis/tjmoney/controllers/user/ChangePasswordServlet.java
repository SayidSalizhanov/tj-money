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
import ru.itis.tjmoney.util.PasswordUtil;

import java.io.IOException;

@WebServlet("/user/changePassword")
public class ChangePasswordServlet extends HttpServlet {
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
        req.getRequestDispatcher("/templates/users/changePassword.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        String currentPassword = userService.getUserById(userId).getPassword();

        String oldPassword = req.getParameter("oldPassword");
        if (!PasswordUtil.encrypt(oldPassword).equalsIgnoreCase(currentPassword)) {
            req.setAttribute("errorMessage", "Неверный старый пароль");
            req.getRequestDispatcher("/templates/users/changePassword.jsp").forward(req, resp);
            return;
        }

        String newPassword = req.getParameter("newPassword");
        String repeatPassword = req.getParameter("repeatPassword");

        if (!newPassword.equals(repeatPassword)) {
            req.setAttribute("errorMessage", "Новые пароли не совпадают");
            req.getRequestDispatcher("/templates/users/changePassword.jsp").forward(req, resp);
            return;
        }

        userService.changePassword(newPassword, userId);
        resp.sendRedirect("/user");
    }
}
