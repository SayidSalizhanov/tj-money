package ru.itis.tjmoney.controllers.transaction;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.dto.TransactionDTO;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.models.GroupMember;
import ru.itis.tjmoney.services.interfaces.IGroupMemberService;
import ru.itis.tjmoney.services.interfaces.ITransactionService;

import java.io.IOException;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {
    private ITransactionService transactionService;
    private IGroupMemberService groupMemberService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        transactionService = (ITransactionService) getServletContext().getAttribute("transactionService");
        groupMemberService = (IGroupMemberService) getServletContext().getAttribute("groupMemberService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int transactionId = Integer.parseInt(req.getParameter("transactionId"));
        int userId = (Integer) req.getSession().getAttribute("userId");
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        getTransactionPage(userId, groupId, transactionId, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        int userId = (Integer) req.getSession().getAttribute("userId");
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);
        int transactionId = Integer.parseInt(req.getParameter("transactionId"));

        if ("DELETE".equals(method)) {
            deleteTransactionPage(userId, groupId, transactionId, req, resp);
        } else if ("PUT".equals(method)) {
            putTransactionPage(
                    userId,
                    groupId,
                    transactionId,
                    Integer.parseInt(req.getParameter("amount")),
                    req.getParameter("category"),
                    req.getParameter("type"),
                    req.getParameter("description"),
                    req,
                    resp
            );
        }
    }

    private void getTransactionPage(int userId, int groupId, int transactionId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TransactionDTO transactionDTO = transactionService.getTransactionDTO(transactionId);

        if (groupId == 0) {
            if (!transactionDTO.getUsername().equals(req.getSession().getAttribute("username"))) {
                resp.sendRedirect("/transactions");
                return;
            }
        }
        else {
            GroupMember groupMember = groupMemberService.getGroupMember(userId, groupId);
            if (!(groupMember.getRole().equalsIgnoreCase("admin") || transactionDTO.getUsername().equals(req.getSession().getAttribute("username")))) {
                resp.sendRedirect("/transactions?groupId=%d".formatted(groupId));
                return;
            }
        }

        req.setAttribute("transaction", transactionDTO);
        req.setAttribute("transactionId", transactionId);
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        if (groupId != 0) req.setAttribute("ownerName", transactionDTO.getUsername());
        req.getRequestDispatcher("/templates/transactions/transaction.jsp").forward(req, resp);
    }

    private void deleteTransactionPage(int userId, int groupId, int transactionId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        transactionService.delete(transactionId);
        if (groupId == 0) resp.sendRedirect("/transactions");
        else resp.sendRedirect("/transactions?groupId=%d".formatted(groupId));
    }

    private void putTransactionPage(int userId, int groupId, int transactionId, int amount, String category, String type, String description, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            transactionService.update(amount, category, type, description, transactionId);
            if (groupId == 0) resp.sendRedirect("/transactions");
            else resp.sendRedirect("/transactions?groupId=%d".formatted(groupId));
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/templates/transactions/transaction.jsp").forward(req, resp);
        }
    }
}
