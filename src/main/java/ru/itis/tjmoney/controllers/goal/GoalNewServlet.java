package ru.itis.tjmoney.controllers.goal;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.interfaces.IGoalService;

import java.io.IOException;

@WebServlet("/goals/new")
public class GoalNewServlet extends HttpServlet {
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

        getGoalNew(
                (Integer) req.getSession().getAttribute("userId"),
                groupId,
                req, resp
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        postGoalNew(
                (Integer) req.getSession().getAttribute("userId"),
                groupId,
                req.getParameter("title"),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("progress")),
                req,
                resp
        );
    }

    private void getGoalNew(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/templates/goals/newGoal.jsp").forward(req, resp);
    }

    private void postGoalNew(int userId, int groupId, String title, String description, int progress, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        goalService.save(userId, groupId, title, description, progress);
        if (groupId == 0) resp.sendRedirect("/goals");
        else resp.sendRedirect("/goals?groupId=%d".formatted(groupId));
    }
}
