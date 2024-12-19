package ru.itis.tjmoney.controllers.record;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.interfaces.IRecordService;

import java.io.IOException;

@WebServlet("/records/new")
public class RecordNewServlet extends HttpServlet {
    private IRecordService recordService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        recordService = (IRecordService) getServletContext().getAttribute("recordService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        getRecordNew(
                (Integer) req.getSession().getAttribute("userId"),
                groupId,
                req, resp
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        postRecordNew(
                (Integer) req.getSession().getAttribute("userId"),
                groupId,
                req.getParameter("title"),
                req.getParameter("content"),
                req,
                resp
        );
    }

    private void getRecordNew(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/templates/records/newRecord.jsp").forward(req, resp);
    }

    private void postRecordNew(int userId, int groupId, String title, String content, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        recordService.save(userId, groupId, title, content);
        if (groupId == 0) resp.sendRedirect("/records");
        else resp.sendRedirect("/records?groupId=%d".formatted(groupId));
    }
}
