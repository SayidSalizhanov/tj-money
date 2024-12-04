package ru.itis.tjmoney.controllers.transaction;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.services.TransactionService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {
    private TransactionService transactionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        transactionService = (TransactionService) getServletContext().getAttribute("transactionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int transactionId = Integer.parseInt(req.getParameter("transactionId"));
        String userIdStr = req.getParameter("userId");
        int userId = userIdStr == null ? 0 : Integer.parseInt(userIdStr);
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        getTransactionPage(userId, groupId, transactionId, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        String userIdStr = req.getParameter("userId");
        int userId = userIdStr == null ? 0 : Integer.parseInt(userIdStr);
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
        req.setAttribute("transaction", transactionService.getTransactionDTO(transactionId));
        req.setAttribute("transactionId", transactionId);
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/templates/transactions/transaction.jsp").forward(req, resp);
    }

    private void deleteTransactionPage(int userId, int groupId, int transactionId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        transactionService.delete(transactionId);
        if (groupId == 0) resp.sendRedirect("/transactions?userId=%d".formatted(userId));
        else resp.sendRedirect("/transactions?userId=%d&groupId=%d".formatted(userId, groupId));
    }

    private void putTransactionPage(int userId, int groupId, int transactionId, int amount, String category, String type, String description, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            transactionService.update(amount, category, type, description, transactionId);
            if (groupId == 0) resp.sendRedirect("/transactions?userId=%d".formatted(userId));
            else resp.sendRedirect("/transactions?userId=%d&groupId=%d".formatted(userId, groupId));
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/templates/transactions/transaction.jsp").forward(req, resp);
        }
    }
}
