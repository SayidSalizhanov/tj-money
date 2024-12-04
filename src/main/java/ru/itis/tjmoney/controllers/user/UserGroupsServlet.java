package ru.itis.tjmoney.controllers.user;

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

@WebServlet("/user/groups")
public class UserGroupsServlet extends HttpServlet {
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

        req.setAttribute("userGroupsDTOs", groupService.getUserGroupsDTOs(userId));
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("/templates/users/userGroups.jsp").forward(req, resp);
    }
}
