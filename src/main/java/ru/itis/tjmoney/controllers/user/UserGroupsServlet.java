package ru.itis.tjmoney.controllers.user;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.interfaces.IGroupService;

import java.io.IOException;

@WebServlet("/user/groups")
public class UserGroupsServlet extends HttpServlet {
    private IGroupService groupService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        groupService = (IGroupService) getServletContext().getAttribute("groupService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");

        req.setAttribute("userGroupsDTOs", groupService.getUserGroupsDTOs(userId));
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("/templates/users/userGroups.jsp").forward(req, resp);
    }
}
