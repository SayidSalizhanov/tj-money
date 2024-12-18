package ru.itis.tjmoney.controllers.reminder;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.ReminderService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/reminders/new")
public class ReminderNewServlet extends HttpServlet {
    private ReminderService reminderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        reminderService = (ReminderService) getServletContext().getAttribute("reminderService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        getReminderNew(
                (Integer) req.getSession().getAttribute("userId"),
                groupId,
                req, resp
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        postReminderNew(
                (Integer) req.getSession().getAttribute("userId"),
                groupId,
                req.getParameter("title"),
                req.getParameter("message"),
                LocalDateTime.parse(req.getParameter("datetime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")),
                req,
                resp
        );
    }

    private void getReminderNew(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/templates/reminders/newReminder.jsp").forward(req, resp);
    }

    private void postReminderNew(int userId, int groupId, String title, String message, LocalDateTime sendAt, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        reminderService.save(userId, groupId, title, message, sendAt);
        if (groupId == 0) resp.sendRedirect("/reminders");
        else resp.sendRedirect("/reminders?groupId=%d".formatted(groupId));
    }
}
