package ru.itis.tjmoney.controllers.group;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.interfaces.IApplicationService;
import ru.itis.tjmoney.services.interfaces.IGroupService;

import java.io.IOException;

@WebServlet("/groups")
public class GroupsServlet extends HttpServlet {
    private IGroupService groupService;
    private IApplicationService applicationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        groupService = (IGroupService) getServletContext().getAttribute("groupService");
        applicationService = (IApplicationService) getServletContext().getAttribute("applicationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");

        getGroups(userId, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");

        postGroups(
                userId,
                Integer.parseInt(req.getParameter("groupId")),
                req,
                resp
        );
    }

    private void getGroups(int userId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("groups", groupService.getGroupsWhereUserNotJoined(userId));
        req.getRequestDispatcher("/templates/groups/groups.jsp").forward(req, resp);
    }

    private void postGroups(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        applicationService.createApplication(userId, groupId);
    }
}
