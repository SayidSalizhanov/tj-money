package ru.itis.tjmoney.controllers.goal;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.interfaces.IGoalService;

import java.io.IOException;

@WebServlet("/goals")
public class GoalsServlet extends HttpServlet {
    private IGoalService goalService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        goalService = (IGoalService) getServletContext().getAttribute("goalService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        getGoalsRequest(
                (Integer) req.getSession().getAttribute("userId"),
                groupId,
                req, resp
        );
    }

    private void getGoalsRequest(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("goals", goalService.getUserAndGroupGoals(userId, groupId));
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("templates/goals/goals.jsp").forward(req, resp);
    }
}
