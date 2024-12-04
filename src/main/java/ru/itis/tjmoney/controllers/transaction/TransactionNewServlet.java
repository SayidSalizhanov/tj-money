package ru.itis.tjmoney.controllers.transaction;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.TransactionService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/transactions/new")
public class TransactionNewServlet extends HttpServlet {
    private TransactionService transactionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        transactionService = (TransactionService) getServletContext().getAttribute("transactionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        getTransactionNew(
                Integer.parseInt(req.getParameter("userId")),
                groupId,
                req, resp
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        postTransactionNew(
                Integer.parseInt(req.getParameter("userId")),
                groupId,
                Integer.parseInt(req.getParameter("amount")),
                req.getParameter("category"),
                req.getParameter("type"),
                LocalDateTime.parse(req.getParameter("datetime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")),
                req.getParameter("description"),
                req,
                resp
        );
    }

    private void getTransactionNew(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/templates/transactions/newTransaction.jsp").forward(req, resp);
    }

    private void postTransactionNew(int userId, int groupId, int amount, String category, String type, LocalDateTime date, String description, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        transactionService.save(userId, groupId, amount, category, type, date, description);
        if (groupId == 0) resp.sendRedirect("/transactions?userId=%d".formatted(userId));
        else resp.sendRedirect("/transactions?userId=%d&groupId=%d".formatted(userId, groupId));
    }
}
