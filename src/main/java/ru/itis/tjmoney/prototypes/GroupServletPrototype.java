package ru.itis.tjmoney.prototypes;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.models.GroupMember;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.services.*;

import java.io.IOException;

/*
@WebServlet("/prototypes/2")
public class GroupServletPrototype extends HttpServlet {
    private UserService userService;
    private TransactionService transactionService;
    private GroupService groupService;
    private ApplicationService applicationService;
    private GroupMemberService groupMemberService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
        transactionService = (TransactionService) getServletContext().getAttribute("transactionService");
        groupService = (GroupService) getServletContext().getAttribute("groupService");
        applicationService = (ApplicationService) getServletContext().getAttribute("applicationService");
        groupMemberService = (GroupMemberService) getServletContext().getAttribute("groupMemberService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = ((User) req.getSession().getAttribute("user")).getId();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            getGroups(userId, req, resp);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts[1].equalsIgnoreCase("new")) {
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
            getGroupRequest(userId, groupId, req, resp);
            return;
        }

        GroupMember groupMember = groupMemberService.getGroupMember(userId, groupId);

        switch (action) {
            case "viewing":
                getGroupViewing(groupId, req, resp);
            case "settings":
                if (groupMember.getRole().equalsIgnoreCase("admin")) getGroupSettings(groupId, req, resp);
                else resp.sendRedirect(req.getContextPath() + "/groups/" + groupId);
                break;
            case "members":
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
        int userId = ((User) req.getSession().getAttribute("user")).getId();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            postGroups(
                    userId,
                    Integer.parseInt(req.getParameter("groupId")),
                    req,
                    resp
            );
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts[1].equalsIgnoreCase("new")) {
            postNewGroup(
                    userId,
                    req.getParameter("name"),
                    req.getParameter("description"),
                    req,
                    resp
            );
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
            getGroupRequest(userId, groupId, req, resp);
            return;
        }

        GroupMember groupMember = groupMemberService.getGroupMember(userId, groupId);

        switch (action) {
            case "viewing":
                getGroupViewing(groupId, req, resp);
            case "settings":
                if (groupMember.getRole().equalsIgnoreCase("admin")) getGroupSettings(groupId, req, resp);
                else resp.sendRedirect(req.getContextPath() + "/groups/" + groupId);
                break;
            case "members":
                if (subAction == null) {
                    if (groupMember.getRole().equalsIgnoreCase("admin")) getGroupMembersAdmin(groupId, req, resp);
                    else getGroupMembers(groupId, req, resp);
                } else if ("applications".equals(subAction)) {
                    if (groupMember.getRole().equalsIgnoreCase("admin")) {
                        postGroupApplications(
                                req.getParameter("username"),
                                groupId,
                                Integer.parseInt(req.getParameter("applicationId")),
                                req.getParameter("applicationStatus"),
                                req,
                                resp
                        );
                    }
                    else resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = ((User) req.getSession().getAttribute("user")).getId();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            getGroups(userId, req, resp);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts[1].equalsIgnoreCase("new")) {
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
            getGroupRequest(userId, groupId, req, resp);
            return;
        }

        GroupMember groupMember = groupMemberService.getGroupMember(userId, groupId);

        switch (action) {
            case "viewing":
                getGroupViewing(groupId, req, resp);
            case "settings":
                if (groupMember.getRole().equalsIgnoreCase("admin"))
                    putGroupSettings(
                        groupId,
                        req.getParameter("name"),
                        req.getParameter("description"),
                        req,
                        resp
                    );
                else resp.sendRedirect(req.getContextPath() + "/groups/" + groupId);
                break;
            case "members":
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = ((User) req.getSession().getAttribute("user")).getId();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            getGroups(userId, req, resp);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts[1].equalsIgnoreCase("new")) {
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
            getGroupRequest(userId, groupId, req, resp);
            return;
        }

        GroupMember groupMember = groupMemberService.getGroupMember(userId, groupId);

        switch (action) {
            case "viewing":
                getGroupViewing(groupId, req, resp);
            case "settings":
                if (groupMember.getRole().equalsIgnoreCase("admin")) deleteGroupSettings(groupId, req, resp);
                else resp.sendRedirect(req.getContextPath() + "/groups/" + groupId);
                break;
            case "members":
                if (subAction == null) {
                    if (groupMember.getRole().equalsIgnoreCase("admin"))
                        deleteGroupMembersAdmin(
                                groupId,
                                req.getParameter("username"),
                                req,
                                resp
                        );
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

    private void getGroups(int userId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("groups", groupService.getGroupsWhereUserNotJoined(userId));
        req.getRequestDispatcher("templates/groups/groups.jsp").forward(req, resp);
    }

    private void getGroupNew(int userId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("templates/groups/newGroup.jsp").forward(req, resp);
    }

    private void getGroupRequest(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("group", groupService.getGroupById(groupId));
        req.setAttribute("transactions", transactionService.getGroupTransactions(groupId, "all"));
        req.setAttribute("groupId", groupId);
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("templates/groups/group.jsp").forward(req, resp);
    }

    private void getGroupViewing(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("group", groupService.getGroupById(groupId));
        req.setAttribute("membersCount", groupMemberService.getMembersByGroupId(groupId).size());
        req.setAttribute("admin", groupService.getAdminUsername(groupId));
        req.getRequestDispatcher("templates/groups/groupViewing.jsp").forward(req, resp);
    }

    private void getGroupSettings(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("group", groupService.getGroupById(groupId));
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("templates/groups/settings.jsp").forward(req, resp);
    }

    private void getGroupMembers(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("members", groupMemberService.getMembersDTO(groupId));
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("templates/groups/members.jsp").forward(req, resp);
    }

    private void getGroupMembersAdmin(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("members", groupMemberService.getMembersDTO(groupId));
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("templates/groups/membersAdmin.jsp").forward(req, resp);
    }

    private void getGroupApplications(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("applications", applicationService.getGroupApplicationUserDTOs(groupId));
        req.getRequestDispatcher("templates/groups/applications.jsp").forward(req, resp);
    }

    private void postGroups(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        applicationService.createApplication(userId, groupId);
    }

    private void postNewGroup(int userId, String name, String description, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        groupService.save(userId, name, description);
        resp.sendRedirect(req.getContextPath() + "/users/" + userId + "/groups");
    }

    private void postGroupApplications(String username, int groupId, int applicationId, String applicationStatus, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = userService.getByUsername(username).getId();
        applicationService.updateStatus(applicationId, applicationStatus);
        if (applicationStatus.equalsIgnoreCase("одобрено")) groupMemberService.save(userId, groupId);
        resp.sendRedirect(req.getContextPath() + "/groups/" + groupId + "/groups");
    }

    private void putGroupSettings(int groupId, String name, String description, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            groupService.update(groupId, name, description);
            resp.sendRedirect(req.getContextPath() + "/groups/" + groupId);
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("templates/groups/settings.jsp").forward(req, resp);
        }
    }

    private void deleteGroupSettings(int groupId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        groupService.delete(groupId);
        resp.sendRedirect(req.getContextPath() + "/mainPage");
    }

    private void deleteGroupMembersAdmin(int groupId, String username, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        groupMemberService.delete(groupMemberService.getByUsernameAndGroupId(username, groupId).getId());
        resp.sendRedirect(req.getContextPath() + "/groups/" + groupId + "/members");
    }
}
*/
