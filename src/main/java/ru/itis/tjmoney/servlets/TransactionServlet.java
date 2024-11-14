package ru.itis.tjmoney.servlets;

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

@WebServlet("/transactions/*")
public class TransactionServlet extends HttpServlet {
    private TransactionService transactionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        transactionService = (TransactionService) getServletContext().getAttribute("transactionService");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String strUserId = req.getParameter("userId");
        String strGroupId = req.getParameter("groupId");

        int userId = Integer.parseInt(strUserId);
        int groupId;
        if (strGroupId != null) groupId = Integer.parseInt(strGroupId);
        else groupId = 0;

        if (pathInfo == null || pathInfo.equals("/")) {
            getTransactionsRequest(userId, groupId, req, resp);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String action = pathParts[1];

        if (action.equals("new")) {
            getTransactionNew(userId, groupId, req, resp);
        } else {
            int transactionId;
            try {
                transactionId = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
                return;
            }

            getTransactionPage(transactionId, req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String strUserId = req.getParameter("userId");
        String strGroupId = req.getParameter("groupId");

        int userId = Integer.parseInt(strUserId);
        int groupId;
        if (strGroupId != null) groupId = Integer.parseInt(strGroupId);
        else groupId = 0;

        if (pathInfo == null || pathInfo.equals("/")) {
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String action = pathParts[1];

        if (action.equals("new")) {
            postTransactionNew(
                    userId,
                    groupId,
                    Integer.parseInt(req.getParameter("amount")),
                    req.getParameter("category"),
                    req.getParameter("type"),
                    LocalDateTime.now(),
                    req.getParameter("description"),
                    req,
                    resp
            );
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String strUserId = req.getParameter("userId");
        String strGroupId = req.getParameter("groupId");

        int userId = Integer.parseInt(strUserId);
        int groupId;
        if (strGroupId != null) groupId = Integer.parseInt(strGroupId);
        else groupId = 0;

        if (pathInfo == null || pathInfo.equals("/")) {
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String action = pathParts[1];

        if (action.equals("new")) {
            req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
        } else {
            int transactionId;
            try {
                transactionId = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
                return;
            }

            putTransactionPage(userId,
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String strUserId = req.getParameter("userId");
        String strGroupId = req.getParameter("groupId");

        int userId = Integer.parseInt(strUserId);
        int groupId;
        if (strGroupId != null) groupId = Integer.parseInt(strGroupId);
        else groupId = 0;

        if (pathInfo == null || pathInfo.equals("/")) {
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String action = pathParts[1];

        if (action.equals("new")) {
            req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
        } else {
            int transactionId;
            try {
                transactionId = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
                return;
            }

            deleteTransactionPage(userId, groupId, transactionId, req, resp);
        }
    }

    private void getTransactionsRequest(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("transactions", transactionService.getUserAndGroupTransactions(userId, groupId));
        req.getRequestDispatcher("templates/transactions/transactions.jsp").forward(req, resp);
    }

    private void getTransactionPage(int transactionId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("transaction", transactionService.getTransaction(transactionId));
        req.getRequestDispatcher("templates/transactions/transaction.jsp").forward(req, resp);
    }

    private void getTransactionNew(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("templates/transactions/newTransaction.jsp").forward(req, resp);
    }

    private void postTransactionNew(int userId, int groupId, int amount, String category, String type, LocalDateTime date, String description, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        transactionService.save(userId, groupId, amount, category, type, date, description);
        if (groupId == 0) resp.sendRedirect(req.getContextPath() + "/transactions?" + "userId=" + userId);
        else resp.sendRedirect(req.getContextPath() + "/transactions?" + "userId=" + userId + "&groupId=" + groupId);
    }

    private void deleteTransactionPage(int userId, int groupId, int transactionId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        transactionService.delete(transactionId);
        if (groupId == 0) resp.sendRedirect(req.getContextPath() + "/transactions?" + "userId=" + userId);
        else resp.sendRedirect(req.getContextPath() + "/transactions?" + "userId=" + userId + "&groupId=" + groupId);
    }

    private void putTransactionPage(int userId, int groupId, int transactionId, int amount, String category, String type, String description, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            transactionService.update(amount, category, type, description, transactionId);
            if (groupId == 0) resp.sendRedirect(req.getContextPath() + "/transactions?" + "userId=" + userId);
            else resp.sendRedirect(req.getContextPath() + "/transactions?" + "userId=" + userId + "&groupId=" + groupId);
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("templates/transactions/transactions.jsp").forward(req, resp);
        }
    }
}
