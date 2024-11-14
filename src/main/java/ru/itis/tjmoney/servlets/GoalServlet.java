package ru.itis.tjmoney.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.services.GoalService;

import java.io.IOException;

@WebServlet("/goals/*")
public class GoalServlet extends HttpServlet {
    private GoalService goalService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        goalService = (GoalService) getServletContext().getAttribute("goalService");
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
            getGoalsRequest(userId, groupId, req, resp);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String action = pathParts[1];

        if (action.equals("new")) {
            getGoalNew(userId, groupId, req, resp);
        } else {
            int goalId;
            try {
                goalId = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
                return;
            }

            getGoalPage(goalId, req, resp);
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
            postGoalNew(
                    userId,
                    groupId,
                    req.getParameter("title"),
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("progress")),
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
            int goalId;
            try {
                goalId = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
                return;
            }

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
            int goalId;
            try {
                goalId = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
                return;
            }

            deleteGoalPage(userId, groupId, goalId, req, resp);
        }
    }

    private void getGoalsRequest(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("goals", goalService.getUserAndGroupGoals(userId, groupId));
        req.getRequestDispatcher("templates/goals/goals.jsp").forward(req, resp);
    }

    private void getGoalPage(int goalId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("goal", goalService.getGoal(goalId));
        req.getRequestDispatcher("templates/goals/goal.jsp").forward(req, resp);
    }

    private void getGoalNew(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("templates/goals/newGoal.jsp").forward(req, resp);
    }

    private void postGoalNew(int userId, int groupId, String title, String description, int progress, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        goalService.save(userId, groupId, title, description, progress);
        if (groupId == 0) resp.sendRedirect(req.getContextPath() + "/goals?" + "userId=" + userId);
        else resp.sendRedirect(req.getContextPath() + "/goals?" + "userId=" + userId + "&groupId=" + groupId);
    }

    private void deleteGoalPage(int userId, int groupId, int goalId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        goalService.delete(goalId);
        if (groupId == 0) resp.sendRedirect(req.getContextPath() + "/goals?" + "userId=" + userId);
        else resp.sendRedirect(req.getContextPath() + "/goals?" + "userId=" + userId + "&groupId=" + groupId);
    }

    private void putGoalPage(int userId, int groupId, int goalId, String title, String description, int progress, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            goalService.update(title, description, progress, goalId);
            if (groupId == 0) resp.sendRedirect(req.getContextPath() + "/goals?" + "userId=" + userId);
            else resp.sendRedirect(req.getContextPath() + "/goals?" + "userId=" + userId + "&groupId=" + groupId);
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("templates/goals/goals.jsp").forward(req, resp);
        }
    }
}
