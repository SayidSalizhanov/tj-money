package ru.itis.tjmoney.controllers.group;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.interfaces.IGroupMemberService;
import ru.itis.tjmoney.services.interfaces.IGroupService;

import java.io.IOException;

@WebServlet("/group/viewing")
public class GroupViewingServlet extends HttpServlet {
    private IGroupService groupService;
    private IGroupMemberService groupMemberService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        groupService = (IGroupService) getServletContext().getAttribute("groupService");
        groupMemberService = (IGroupMemberService) getServletContext().getAttribute("groupMemberService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int groupId = Integer.parseInt(req.getParameter("groupId"));

        getGroupViewing(groupId, req, resp);
    }

    private void getGroupViewing(int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("group", groupService.getGroupDTOById(groupId));
        req.setAttribute("membersCount", groupMemberService.getMembersByGroupId(groupId).size());
        req.setAttribute("admin", groupService.getAdminUsername(groupId));
        req.getRequestDispatcher("/templates/groups/groupViewing.jsp").forward(req, resp);
    }
}
