package ru.itis.tjmoney.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.models.GroupMember;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.services.*;

import java.io.IOException;

@WebServlet("/groups/*")
public class GroupServlet extends HttpServlet {
    private UserService userService;
    private TransactionService transactionService;
    private GroupService groupService;
    private ApplicationService applicationService;
    private GroupMemberService groupMemberService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        transactionService = (TransactionService) getServletContext().getAttribute("transactionService");
        groupService = (GroupService) getServletContext().getAttribute("groupService");
        applicationService = (ApplicationService) getServletContext().getAttribute("applicationService");
        groupMemberService = (GroupMemberService) getServletContext().getAttribute("groupMemberService");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            getGroups(req, resp);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts[1].equalsIgnoreCase("new")) {
            int userId = Integer.parseInt(req.getParameter("userId"));
            getGroupNew(userId, req, resp);
            return;
        }
        String idStr = pathParts[1];
        int groupId;
        try {
            groupId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
            return;
        }

        String action = (pathParts.length > 2) ? pathParts[2] : null;
        String subAction = (pathParts.length > 3) ? pathParts[3] : null;

        if (action == null) {
            getGroupRequest(groupId, req, resp);
            return;
        }

        switch (action) {
            case "viewing":
                getGroupViewing(groupId, req, resp);
            case "settings":
                getGroupSettings(groupId, req, resp);
                break;
            case "members":
                GroupMember groupMember = groupMemberService.getGroupMember(((User) req.getSession().getAttribute("user")).getId(), groupId);
                if (subAction == null) {
                    if (groupMember.getRole().equalsIgnoreCase("admin")) getGroupMembersAdmin(groupId, req, resp);
                    else getGroupMembers(groupId, req, resp);
                } else if ("applications".equals(subAction)) {
                    if (groupMember.getRole().equalsIgnoreCase("admin")) getGroupApplications(groupId, req, resp);
                    else resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            getGroups(req, resp);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts[1].equalsIgnoreCase("new")) {
            int userId = Integer.parseInt(req.getParameter("userId"));
            getGroupNew(userId, req, resp);
            return;
        }
        String idStr = pathParts[1];
        int groupId;
        try {
            groupId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
            return;
        }

        String action = (pathParts.length > 2) ? pathParts[2] : null;
        String subAction = (pathParts.length > 3) ? pathParts[3] : null;

        if (action == null) {
            getGroupRequest(groupId, req, resp);
            return;
        }

        switch (action) {
            case "viewing":
                getGroupViewing(groupId, req, resp);
            case "settings":
                getGroupSettings(groupId, req, resp);
                break;
            case "members":
                GroupMember groupMember = groupMemberService.getGroupMember(((User) req.getSession().getAttribute("user")).getId(), groupId);
                if (subAction == null) {
                    if (groupMember.getRole().equalsIgnoreCase("admin")) getGroupMembersAdmin(groupId, req, resp);
                    else getGroupMembers(groupId, req, resp);
                } else if ("applications".equals(subAction)) {
                    if (groupMember.getRole().equalsIgnoreCase("admin")) getGroupApplications(groupId, req, resp);
                    else resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    private void getGroups(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("groups", groupService.getAllGroups());
        req.getRequestDispatcher("templates/groups/groups.jsp").forward(req, resp);
    }

    private void getGroupNew(int userId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("templates/groups/newGroup.jsp").forward(req, resp);
    }

    private void getGroupRequest(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("group", groupService.getGroupById(groupId));
        req.setAttribute("transactions", transactionService.getGroupTransactions(groupId));
        req.getRequestDispatcher("templates/groups/group.jsp").forward(req, resp);
    }

    private void getGroupViewing(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("group", groupService.getGroupById(groupId));
        req.getRequestDispatcher("templates/groups/groupViewing.jsp").forward(req, resp);
    }

    private void getGroupSettings(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("group", groupService.getGroupById(groupId));
        req.getRequestDispatcher("templates/groups/settings.jsp").forward(req, resp);
    }

    private void getGroupMembers(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("members", groupMemberService.getMembersByGroupId(groupId));
        req.getRequestDispatcher("templates/groups/members.jsp").forward(req, resp);
    }

    private void getGroupMembersAdmin(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("members", groupMemberService.getMembersByGroupId(groupId));
        req.getRequestDispatcher("templates/groups/membersAdmin.jsp").forward(req, resp);
    }

    private void getGroupApplications(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("applications", applicationService.getGroupApplicationUserDTOs(groupId));
        req.getRequestDispatcher("templates/groups/applications.jsp").forward(req, resp);
    }
}
