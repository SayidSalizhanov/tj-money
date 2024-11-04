package ru.itis.tjmoney.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.TransactionService;
import ru.itis.tjmoney.services.UserService;

import java.io.IOException;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    private UserService userService;
    private TransactionService transactionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        transactionService = (TransactionService) getServletContext().getAttribute("transactionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String idStr = pathParts[1];
        int userId;
        try {
            userId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
            return;
        }

        String action = (pathParts.length > 2) ? pathParts[2] : null;
        String subAction = (pathParts.length > 3) ? pathParts[3] : null;

        if (action == null) {
            handleUserRequest(userId, req, resp);
            return;
        }

        switch (action) {
            case "goals":
                if (subAction == null) {
                    handleUserGoals(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    handleUserGoalsNew(userId, req, resp);
                } else {
                    handleUserGoalDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "reminders":
                if (subAction == null) {
                    handleUserReminders(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    handleUserRemindersNew(userId, req, resp);
                } else {
                    handleUserReminderDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "records":
                if (subAction == null) {
                    handleUserRecords(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    handleUserRecordsNew(userId, req, resp);
                } else {
                    handleUserRecordDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "transactions":
                if (subAction == null) {
                    handleUserTransactions(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    handleUserTransactionsNew(userId, req, resp);
                } else {
                    handleUserTransactionDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "settings":
                handleUserSettings(userId, req, resp);
                break;
            case "groups":
                if (subAction == null) {
                    handleUserGroups(userId, req, resp);
                } else if ("applications".equals(subAction)) {
                    handleUserGroupApplications(userId, req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
    }

    private void handleUserRequest(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("user", userService.getUserById(userId));
        req.setAttribute("transactions", transactionService.getUserTransactions(userId));
        req.getRequestDispatcher("templates/userProfile.jsp").forward(req, resp);
    }

    private void handleUserGoals(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Goals for User ID: " + userId);
    }

    private void handleUserGoalsNew(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("New Goal for User ID: " + userId);
    }

    private void handleUserGoalDetail(int userId, int goalId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Goal ID: " + goalId + " for User ID: " + userId);
    }

    private void handleUserReminders(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Reminders for User ID: " + userId);
    }

    private void handleUserRemindersNew(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("New Reminder for User ID: " + userId);
    }

    private void handleUserReminderDetail(int userId, int reminderId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Reminder ID: " + reminderId + " for User ID: " + userId);
    }

    private void handleUserRecords(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Records for User ID: " + userId);
    }

    private void handleUserRecordsNew(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("New Record for User ID: " + userId);
    }

    private void handleUserRecordDetail(int userId, int recordId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Reminder ID: " + recordId + " for User ID: " + userId);
    }

    private void handleUserTransactions(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Records for User ID: " + userId);
    }

    private void handleUserTransactionsNew(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("New Record for User ID: " + userId);
    }

    private void handleUserTransactionDetail(int userId, int transactionId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Reminder ID: " + transactionId + " for User ID: " + userId);
    }

    private void handleUserSettings(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Records for User ID: " + userId);
    }

    private void handleUserGroups(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("New Record for User ID: " + userId);
    }

    private void handleUserGroupApplications(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Reminder ID: " + userId + " for User ID: " + userId);
    }
}