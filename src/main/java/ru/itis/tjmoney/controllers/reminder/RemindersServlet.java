package ru.itis.tjmoney.controllers.reminder;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.ReminderService;

import java.io.IOException;

@WebServlet("/reminders")
public class RemindersServlet extends HttpServlet {
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

        getRemindersRequest(
                Integer.parseInt(req.getParameter("userId")),
                groupId,
                req, resp
        );
    }

    private void getRemindersRequest(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("reminders", reminderService.getUserAndGroupReminders(userId, groupId));
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("templates/reminders/reminders.jsp").forward(req, resp);
    }
}
