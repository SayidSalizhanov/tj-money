package ru.itis.tjmoney.controllers.group;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.interfaces.IGroupService;

import java.io.IOException;

@WebServlet("/groups/new")
public class GroupNewServlet extends HttpServlet {
    private IGroupService groupService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        groupService = (IGroupService) getServletContext().getAttribute("groupService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");

        getGroupNew(userId, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");

        postNewGroup(
                userId,
                req.getParameter("name"),
                req.getParameter("description"),
                req,
                resp
        );
    }

    private void getGroupNew(int userId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("/templates/groups/newGroup.jsp").forward(req, resp);
    }

    private void postNewGroup(int userId, String name, String description, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        groupService.save(userId, name, description);
        resp.sendRedirect("/user/groups");
    }
}
