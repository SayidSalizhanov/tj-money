package ru.itis.tjmoney.controllers.group;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.*;

import java.io.IOException;

@WebServlet("/group/viewing")
public class GroupViewingServlet extends HttpServlet {
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
        int groupId = (Integer) req.getSession().getAttribute("userId");

        getGroupViewing(groupId, req, resp);
    }

    private void getGroupViewing(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("group", groupService.getGroupDTOById(groupId));
        req.setAttribute("membersCount", groupMemberService.getMembersByGroupId(groupId).size());
        req.setAttribute("admin", groupService.getAdminUsername(groupId));
        req.getRequestDispatcher("/templates/groups/groupViewing.jsp").forward(req, resp);
    }
}
