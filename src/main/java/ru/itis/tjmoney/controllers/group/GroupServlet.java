package ru.itis.tjmoney.controllers.group;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/group")
public class GroupServlet extends HttpServlet {
    private TransactionService transactionService;
    private GroupService groupService;
    private GroupMemberService groupMemberService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        transactionService = (TransactionService) getServletContext().getAttribute("transactionService");
        groupService = (GroupService) getServletContext().getAttribute("groupService");
        groupMemberService = (GroupMemberService) getServletContext().getAttribute("groupMemberService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        int groupId = Integer.parseInt(req.getParameter("groupId"));

        req.setAttribute("group", groupService.getGroupDTOById(groupId));
        req.setAttribute("role", groupMemberService.getGroupMember(userId, groupId).getRole());

        List<Map<String, Integer>> transactionsGenerals = transactionService.getGroupTransactionsGenerals(groupId);

        req.setAttribute("income", transactionsGenerals.get(0).values().stream().mapToInt(Integer::intValue).sum());
        req.setAttribute("expense", transactionsGenerals.get(1).values().stream().mapToInt(Integer::intValue).sum());

        for (Map.Entry<String, Integer> entry : transactionsGenerals.get(0).entrySet()) {
            if (entry.getKey().equalsIgnoreCase("Другое")) req.setAttribute("Другие_доходы", entry.getValue());
            else req.setAttribute(entry.getKey().replaceAll("\\s+", "_"), entry.getValue());
        }
        for (Map.Entry<String, Integer> entry : transactionsGenerals.get(1).entrySet()) {
            if (entry.getKey().equalsIgnoreCase("Другое")) req.setAttribute("Другие_расходы", entry.getValue());
            else req.setAttribute(entry.getKey().replaceAll("\\s+", "_"), entry.getValue());
        }

        req.setAttribute("groupId", groupId);
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("/templates/groups/group.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        int groupId = Integer.parseInt(req.getParameter("groupId"));

        groupMemberService.deleteByUserIdAndGroupId(userId, groupId);
        resp.sendRedirect("/mainPage");
    }
}
