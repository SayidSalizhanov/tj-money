package ru.itis.tjmoney.controllers.reminder;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.services.interfaces.IReminderService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/reminder")
public class ReminderServlet extends HttpServlet {
    private IReminderService reminderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        reminderService = (IReminderService) getServletContext().getAttribute("reminderService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int reminderId = Integer.parseInt(req.getParameter("reminderId"));
        int userId = (Integer) req.getSession().getAttribute("userId");
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        getReminderPage(userId, groupId, reminderId, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        int userId = (Integer) req.getSession().getAttribute("userId");
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);
        int reminderId = Integer.parseInt(req.getParameter("reminderId"));

        if ("DELETE".equals(method)) {
            deleteReminderPage(userId, groupId, reminderId, req, resp);
        } else if ("PUT".equals(method)) {
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

    private void getReminderPage(int userId, int groupId, int reminderId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("reminder", reminderService.getReminder(reminderId));
        req.setAttribute("reminderId", reminderId);
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/templates/reminders/reminder.jsp").forward(req, resp);
    }

    private void deleteReminderPage(int userId, int groupId, int reminderId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        reminderService.delete(reminderId);
        if (groupId == 0) resp.sendRedirect("/reminders");
        else resp.sendRedirect("/reminders?groupId=%d".formatted(groupId));
    }

    private void putReminderPage(int userId, int groupId, int reminderId, String title, String message, LocalDateTime sendAt, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            reminderService.update(title, message, sendAt, reminderId);
            if (groupId == 0) resp.sendRedirect("/reminders");
            else resp.sendRedirect("/reminders?groupId=%d".formatted(groupId));
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/templates/reminders/reminder.jsp").forward(req, resp);
        }
    }
}
