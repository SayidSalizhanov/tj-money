package ru.itis.tjmoney.controllers.group;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.models.GroupMember;
import ru.itis.tjmoney.services.*;

import java.io.IOException;

@WebServlet("/group/applications")
public class GroupApplicationsServlet extends HttpServlet {
    private UserService userService;
    private ApplicationService applicationService;
    private GroupMemberService groupMemberService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
        applicationService = (ApplicationService) getServletContext().getAttribute("applicationService");
        groupMemberService = (GroupMemberService) getServletContext().getAttribute("groupMemberService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        int groupId = Integer.parseInt(req.getParameter("groupId"));

        GroupMember groupMember = groupMemberService.getGroupMember(userId, groupId);
        if (groupMember.getRole().equalsIgnoreCase("ADMIN")) getGroupApplications(userId, groupId, req, resp);
        else resp.sendRedirect("/group/members?groupId=%d".formatted(groupId));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        int groupId = Integer.parseInt(req.getParameter("groupId"));

        GroupMember groupMember = groupMemberService.getGroupMember(userId, groupId);
        if (groupMember.getRole().equalsIgnoreCase("ADMIN")) {
            postGroupApplications(
                    userId,
                    groupId,
                    req.getParameter("username"),
                    Integer.parseInt(req.getParameter("applicationId")),
                    req.getParameter("applicationStatus"),
                    req,
                    resp
            );
        }
        else resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void getGroupApplications(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("applications", applicationService.getGroupApplicationUserDTOs(groupId));
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/templates/groups/applications.jsp").forward(req, resp);
    }

    private void postGroupApplications(int userId, int groupId, String username, int applicationId, String applicationStatus, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userIdForDelete = userService.getByUsername(username).getId();
        applicationService.updateStatus(applicationId, applicationStatus);
        if (applicationStatus.equalsIgnoreCase("Одобрено")) groupMemberService.save(userIdForDelete, groupId);
//        resp.sendRedirect("/group/applications?groupId=%d".formatted(groupId));
    }
}
