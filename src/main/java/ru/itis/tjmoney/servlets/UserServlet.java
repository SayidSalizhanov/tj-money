package ru.itis.tjmoney.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
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
            getUserRequest(userId, req, resp);
            return;
        }

        switch (action) {
            case "goals":
                if (subAction == null) {
                    getUserGoals(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserGoalsNew(userId, req, resp);
                } else {
                    getUserGoalDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "reminders":
                if (subAction == null) {
                    getUserReminders(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserRemindersNew(userId, req, resp);
                } else {
                    getUserReminderDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "records":
                if (subAction == null) {
                    getUserRecords(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserRecordsNew(userId, req, resp);
                } else {
                    getUserRecordDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "transactions":
                if (subAction == null) {
                    getUserTransactions(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserTransactionsNew(userId, req, resp);
                } else {
                    getUserTransactionDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "settings":
                getUserSettings(userId, req, resp);
                break;
            case "groups":
                if (subAction == null) {
                    getUserGroups(userId, req, resp);
                } else if ("applications".equals(subAction)) {
                    getUserGroupApplications(userId, req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String idStr = pathParts[1];
        int userId;
        try {
            userId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return;
        }

        String action = (pathParts.length > 2) ? pathParts[2] : null;
        String subAction = (pathParts.length > 3) ? pathParts[3] : null;

        if (action == null) {
            return;
        }

        switch (action) {
            case "goals":
                if (subAction == null) {
                    return;
                } else if ("new".equals(subAction)) {
                    getUserGoalsNew(userId, req, resp);
                } else {
                    getUserGoalDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "reminders":
                if (subAction == null) {
                    getUserReminders(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserRemindersNew(userId, req, resp);
                } else {
                    getUserReminderDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "records":
                if (subAction == null) {
                    getUserRecords(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserRecordsNew(userId, req, resp);
                } else {
                    getUserRecordDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "transactions":
                if (subAction == null) {
                    getUserTransactions(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserTransactionsNew(userId, req, resp);
                } else {
                    getUserTransactionDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "settings":
                break;
            case "groups":
                if (subAction == null) {
                    getUserGroups(userId, req, resp);
                } else if ("applications".equals(subAction)) {
                    getUserGroupApplications(userId, req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String idStr = pathParts[1];
        int userId;
        try {
            userId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return;
        }

        String action = (pathParts.length > 2) ? pathParts[2] : null;
        String subAction = (pathParts.length > 3) ? pathParts[3] : null;

        if (action == null) {
            return;
        }

        switch (action) {
            case "goals":
                if (subAction == null) {
                    getUserGoals(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserGoalsNew(userId, req, resp);
                } else {
                    getUserGoalDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "reminders":
                if (subAction == null) {
                    getUserReminders(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserRemindersNew(userId, req, resp);
                } else {
                    getUserReminderDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "records":
                if (subAction == null) {
                    getUserRecords(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserRecordsNew(userId, req, resp);
                } else {
                    getUserRecordDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "transactions":
                if (subAction == null) {
                    getUserTransactions(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserTransactionsNew(userId, req, resp);
                } else {
                    getUserTransactionDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "settings":
                putUserSettings(userId,
                        req.getParameter("username"),
                        req.getParameter("password"),
                        req.getParameter("newPassword"),
                        req.getParameter("repeatPassword"),
                        req.getParameter("telegram_id"),
                        Boolean.parseBoolean(req.getParameter("sending_to_telegram")),
                        Boolean.parseBoolean(req.getParameter("sending_to_email")),
                        req, resp);
                break;
            case "groups":
                if (subAction == null) {
                    getUserGroups(userId, req, resp);
                } else if ("applications".equals(subAction)) {
                    getUserGroupApplications(userId, req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String idStr = pathParts[1];
        int userId;
        try {
            userId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return;
        }

        String action = (pathParts.length > 2) ? pathParts[2] : null;
        String subAction = (pathParts.length > 3) ? pathParts[3] : null;

        if (action == null) {
            return;
        }

        switch (action) {
            case "goals":
                if (subAction == null) {
                    getUserGoals(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserGoalsNew(userId, req, resp);
                } else {
                    getUserGoalDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "reminders":
                if (subAction == null) {
                    getUserReminders(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserRemindersNew(userId, req, resp);
                } else {
                    getUserReminderDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "records":
                if (subAction == null) {
                    getUserRecords(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserRecordsNew(userId, req, resp);
                } else {
                    getUserRecordDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "transactions":
                if (subAction == null) {
                    getUserTransactions(userId, req, resp);
                } else if ("new".equals(subAction)) {
                    getUserTransactionsNew(userId, req, resp);
                } else {
                    getUserTransactionDetail(userId, Integer.parseInt(subAction), req, resp);
                }
                break;
            case "settings":
                deleteUserSettings(userId, req, resp);
                break;
            case "groups":
                if (subAction == null) {
                    getUserGroups(userId, req, resp);
                } else if ("applications".equals(subAction)) {
                    getUserGroupApplications(userId, req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
        }
    }

    //=====

    private void getUserRequest(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("user", userService.getUserById(userId));
        req.setAttribute("transactions", transactionService.getUserTransactions(userId));
        req.getRequestDispatcher("templates/userProfile.jsp").forward(req, resp);
    }

    //=====

    private void getUserGoals(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Goals for User ID: " + userId);
    }

    private void getUserGoalsNew(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("New Goal for User ID: " + userId);
    }

    private void getUserGoalDetail(int userId, int goalId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Goal ID: " + goalId + " for User ID: " + userId);
    }

    //=====

    private void getUserReminders(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Reminders for User ID: " + userId);
    }

    private void getUserRemindersNew(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("New Reminder for User ID: " + userId);
    }

    private void getUserReminderDetail(int userId, int reminderId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Reminder ID: " + reminderId + " for User ID: " + userId);
    }

    //=====

    private void getUserRecords(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Records for User ID: " + userId);
    }

    private void getUserRecordsNew(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("New Record for User ID: " + userId);
    }

    private void getUserRecordDetail(int userId, int recordId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Reminder ID: " + recordId + " for User ID: " + userId);
    }

    //=====

    private void getUserTransactions(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Records for User ID: " + userId);
    }

    private void getUserTransactionsNew(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("New Record for User ID: " + userId);
    }

    private void getUserTransactionDetail(int userId, int transactionId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Reminder ID: " + transactionId + " for User ID: " + userId);
    }

    //=====

    private void getUserSettings(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("user", userService.getUserById(userId));
        req.getRequestDispatcher("templates/users/userSettings.jsp").forward(req, resp);
    }

    private void putUserSettings(int userId, String username, String password, String newPassword, String repeatPassword, String telegramId, boolean sendingToTelegram, boolean sendingToEmail, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            userService.update(userId, username, password, newPassword, repeatPassword, telegramId, sendingToTelegram, sendingToEmail);
            resp.sendRedirect(req.getContextPath() + "/users/" + userId);
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("templates/users/settings.jsp").forward(req, resp);
        }
    }

    private void deleteUserSettings(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        userService.delete(userId);
        resp.sendRedirect(req.getContextPath() + "/mainPage");
    }

    //=====

    private void getUserGroups(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("New Record for User ID: " + userId);
    }

    private void getUserGroupApplications(int userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Reminder ID: " + userId + " for User ID: " + userId);
    }
}