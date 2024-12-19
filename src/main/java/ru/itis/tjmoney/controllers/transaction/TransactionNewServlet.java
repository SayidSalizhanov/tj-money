package ru.itis.tjmoney.controllers.transaction;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.TransactionException;
import ru.itis.tjmoney.services.interfaces.ITransactionService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/transactions/new")
public class TransactionNewServlet extends HttpServlet {
    private ITransactionService transactionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        transactionService = (ITransactionService) getServletContext().getAttribute("transactionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        getTransactionNew(
                (Integer) req.getSession().getAttribute("userId"),
                groupId,
                req, resp
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        postTransactionNew(
                (Integer) req.getSession().getAttribute("userId"),
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

    private void postTransactionNew(int userId, int groupId, int amount, String category, String type, LocalDateTime date, String description, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            transactionService.save(userId, groupId, amount, category, type, date, description);
            if (groupId == 0) resp.sendRedirect("/transactions");
            else resp.sendRedirect("/transactions?groupId=%d".formatted(groupId));
        } catch (TransactionException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("userId", userId);
            req.setAttribute("groupId", groupId);
            req.getRequestDispatcher("/templates/transactions/newTransaction.jsp").forward(req, resp);
        }
    }
}
