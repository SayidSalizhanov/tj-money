package ru.itis.tjmoney.controllers.group;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.*;

import java.io.IOException;

@WebServlet("/group")
public class GroupServlet extends HttpServlet {
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
        int userId = Integer.parseInt(req.getParameter("userId"));
        int groupId = Integer.parseInt(req.getParameter("groupId"));

        getGroupRequest(userId, groupId, req, resp);
    }

    private void getGroupRequest(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("group", groupService.getGroupById(groupId));
        req.setAttribute("transactions", transactionService.getGroupTransactions(groupId));
        req.setAttribute("groupId", groupId);
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("/templates/groups/group.jsp").forward(req, resp);
    }
}
