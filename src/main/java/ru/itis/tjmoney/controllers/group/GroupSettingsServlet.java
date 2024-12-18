package ru.itis.tjmoney.controllers.group;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.models.GroupMember;
import ru.itis.tjmoney.services.*;

import java.io.IOException;

@WebServlet("/group/settings")
public class GroupSettingsServlet extends HttpServlet {
    private GroupService groupService;
    private GroupMemberService groupMemberService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        groupService = (GroupService) getServletContext().getAttribute("groupService");
        groupMemberService = (GroupMemberService) getServletContext().getAttribute("groupMemberService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        int groupId = Integer.parseInt(req.getParameter("groupId"));

        GroupMember groupMember = groupMemberService.getGroupMember(userId, groupId);
        if (groupMember.getRole().equalsIgnoreCase("admin")) getGroupSettings(userId, groupId, req, resp);
        else resp.sendRedirect("/group?groupId=%d".formatted(groupId));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        int groupId = Integer.parseInt(req.getParameter("groupId"));

        GroupMember groupMember = groupMemberService.getGroupMember(userId, groupId);
        if (!groupMember.getRole().equalsIgnoreCase("admin")) resp.sendRedirect("/group?groupId=%d".formatted(groupId));

        String method = req.getParameter("_method");
        if ("DELETE".equals(method)) {
            deleteGroupSettings(userId, groupId, req, resp);
        } else if ("PUT".equals(method)) {
            putGroupSettings(
                    userId,
                    groupId,
                    req.getParameter("name"),
                    req.getParameter("description"),
                    req,
                    resp
            );
        }
    }

    private void getGroupSettings(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("group", groupService.getGroupById(groupId));
        req.setAttribute("groupId", groupId);
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("/templates/groups/settings.jsp").forward(req, resp);
    }

    private void putGroupSettings(int userId, int groupId, String name, String description, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            groupService.update(groupId, name, description);
            resp.sendRedirect("/group?groupId=%d".formatted(groupId));
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("group", groupService.getGroupById(groupId));
            req.setAttribute("groupId", groupId);
            req.setAttribute("userId", userId);
            req.getRequestDispatcher("/templates/groups/settings.jsp").forward(req, resp);
        }
    }

    private void deleteGroupSettings(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        groupService.delete(groupId);
        resp.sendRedirect("/user/groups?groupId=%d".formatted(groupId));
    }
}
