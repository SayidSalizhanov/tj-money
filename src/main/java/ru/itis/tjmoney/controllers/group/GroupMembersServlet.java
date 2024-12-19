package ru.itis.tjmoney.controllers.group;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.models.GroupMember;
import ru.itis.tjmoney.services.interfaces.IGroupMemberService;
import ru.itis.tjmoney.services.interfaces.IUserService;

import java.io.IOException;

@WebServlet("/group/members")
public class GroupMembersServlet extends HttpServlet {
    private IUserService userService;
    private IGroupMemberService groupMemberService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (IUserService) getServletContext().getAttribute("userService");
        groupMemberService = (IGroupMemberService) getServletContext().getAttribute("groupMemberService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        int groupId = Integer.parseInt(req.getParameter("groupId"));

        GroupMember groupMember = groupMemberService.getGroupMember(userId, groupId);
        if (groupMember.getRole().equalsIgnoreCase("admin")) getGroupMembersAdmin(userId, groupId, req, resp);
        else getGroupMembers(userId, groupId, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        int groupId = Integer.parseInt(req.getParameter("groupId"));

        GroupMember groupMember = groupMemberService.getGroupMember(userId, groupId);
        if (groupMember.getRole().equalsIgnoreCase("admin")) {
            String method = req.getParameter("_method");
            if ("DELETE".equals(method))
                deleteGroupMembersAdmin(
                    userId,
                    groupId,
                    req.getParameter("username"),
                    req, resp
                );
        }
        else resp.sendRedirect("/group/members?groupId=%d".formatted(groupId));
    }

    private void getGroupMembers(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("members", groupMemberService.getMembersDTO(groupId));
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/templates/groups/members.jsp").forward(req, resp);
    }

    private void getGroupMembersAdmin(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("members", groupMemberService.getMembersDTO(groupId));
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/templates/groups/membersAdmin.jsp").forward(req, resp);
    }

    private void deleteGroupMembersAdmin(int userId, int groupId, String username, HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        groupMemberService.delete(groupMemberService.getByUsernameAndGroupId(username, groupId).getId());
        groupMemberService.deleteByUserIdAndGroupId(userService.getByUsername(username).getId(), groupId);
    }
}
