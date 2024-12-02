package ru.itis.tjmoney.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.services.ReminderService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/reminders/*")
public class ReminderServlet extends HttpServlet {
    private ReminderService reminderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        reminderService = (ReminderService) getServletContext().getAttribute("reminderService");
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
            getRemindersRequest(userId, groupId, req, resp);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String action = pathParts[1];

        if (action.equals("new")) {
            getReminderNew(userId, groupId, req, resp);
        } else {
            int reminderId;
            try {
                reminderId = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
                return;
            }

            getReminderPage(reminderId, req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");
        if ("DELETE".equals(method)) {
            doDelete(req, resp);
        } else if ("PUT".equals(method)) {
            doPut(req, resp);
        }

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
            postReminderNew(
                    userId,
                    groupId,
                    req.getParameter("title"),
                    req.getParameter("message"),
                    LocalDateTime.of(
                            LocalDate.parse(req.getParameter("date")),
                            LocalTime.parse(req.getParameter("time"))
                    ),
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
            int reminderId;
            try {
                reminderId = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
                return;
            }

            putReminderPage(userId,
                    groupId,
                    reminderId,
                    req.getParameter("title"),
                    req.getParameter("message"),
                    LocalDateTime.parse(req.getParameter("datetime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")),
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
            int reminderId;
            try {
                reminderId = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
                return;
            }

            deleteReminderPage(userId, groupId, reminderId, req, resp);
        }
    }

    private void getRemindersRequest(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("reminders", reminderService.getUserAndGroupReminders(userId, groupId));
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("templates/reminders/reminders.jsp").forward(req, resp);
    }

    private void getReminderPage(int reminderId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("reminder", reminderService.getReminder(reminderId));
        req.setAttribute("reminderId", reminderId);
        req.getRequestDispatcher("templates/reminders/reminder.jsp").forward(req, resp);
    }

    private void getReminderNew(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("templates/reminders/newReminder.jsp").forward(req, resp);
    }

    private void postReminderNew(int userId, int groupId, String title, String message, LocalDateTime sendAt, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        reminderService.save(userId, groupId, title, message, sendAt);
        if (groupId == 0) resp.sendRedirect(req.getContextPath() + "/reminders?" + "userId=" + userId);
        else resp.sendRedirect(req.getContextPath() + "/reminders?" + "userId=" + userId + "&groupId=" + groupId);
    }

    private void deleteReminderPage(int userId, int groupId, int reminderId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        reminderService.delete(reminderId);
        if (groupId == 0) resp.sendRedirect(req.getContextPath() + "/reminders?" + "userId=" + userId);
        else resp.sendRedirect(req.getContextPath() + "/reminders?" + "userId=" + userId + "&groupId=" + groupId);
    }

    private void putReminderPage(int userId, int groupId, int reminderId, String title, String message, LocalDateTime sendAt, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            reminderService.update(title, message, sendAt, reminderId);
            if (groupId == 0) resp.sendRedirect(req.getContextPath() + "/reminders?" + "userId=" + userId);
            else resp.sendRedirect(req.getContextPath() + "/reminders?" + "userId=" + userId + "&groupId=" + groupId);
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("templates/reminders/reminders.jsp").forward(req, resp);
        }
    }
}
