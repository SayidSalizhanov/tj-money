package ru.itis.tjmoney.controllers.goal;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.services.interfaces.IGoalService;

import java.io.IOException;

@WebServlet("/goal")
public class GoalServlet extends HttpServlet {
    private IGoalService goalService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        goalService = (IGoalService) getServletContext().getAttribute("goalService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int goalId = Integer.parseInt(req.getParameter("goalId"));
        int userId = (Integer) req.getSession().getAttribute("userId");
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        getGoalPage(userId, groupId, goalId, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        int userId = (Integer) req.getSession().getAttribute("userId");
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);
        int goalId = Integer.parseInt(req.getParameter("goalId"));

        if ("DELETE".equals(method)) {
            deleteGoalPage(userId, groupId, goalId, req, resp);
        } else if ("PUT".equals(method)) {
            putGoalPage(userId,
                    groupId,
                    goalId,
                    req.getParameter("title"),
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("progress")),
                    req,
                    resp
            );
        }
    }

    private void getGoalPage(int userId, int groupId, int goalId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("goal", goalService.getGoal(goalId));
        req.setAttribute("goalId", goalId);
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/templates/goals/goal.jsp").forward(req, resp);
    }

    private void deleteGoalPage(int userId, int groupId, int goalId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        goalService.delete(goalId);
        if (groupId == 0) resp.sendRedirect("/goals");
        else resp.sendRedirect("/goals?groupId=%d".formatted(groupId));
    }

    private void putGoalPage(int userId, int groupId, int goalId, String title, String description, int progress, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            goalService.update(title, description, progress, goalId);
            if (groupId == 0) resp.sendRedirect("/goals");
            else resp.sendRedirect("/goals?groupId=%d".formatted(groupId));
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/templates/goals/goal.jsp").forward(req, resp);
        }
    }
}
