package ru.itis.tjmoney.controllers.record;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.services.RecordService;

import java.io.IOException;

@WebServlet("/record")
public class RecordServlet extends HttpServlet {
    private RecordService recordService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        recordService = (RecordService) getServletContext().getAttribute("recordService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int recordId = Integer.parseInt(req.getParameter("recordId"));
        int userId = (Integer) req.getSession().getAttribute("userId");
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        getRecordPage(userId, groupId, recordId, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        int userId = (Integer) req.getSession().getAttribute("userId");
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);
        int recordId = Integer.parseInt(req.getParameter("recordId"));

        if ("DELETE".equals(method)) {
            deleteRecordPage(userId, groupId, recordId, req, resp);
        } else if ("PUT".equals(method)) {
            putRecordPage(
                    userId,
                    groupId,
                    recordId,
                    req.getParameter("title"),
                    req.getParameter("content"),
                    req,
                    resp
            );
        }
    }

    private void getRecordPage(int userId, int groupId, int recordId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("record", recordService.getRecord(recordId));
        req.setAttribute("recordId", recordId);
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/templates/records/record.jsp").forward(req, resp);
    }

    private void deleteRecordPage(int userId, int groupId, int recordId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        recordService.delete(recordId);
        if (groupId == 0) resp.sendRedirect("/records");
        else resp.sendRedirect("/records?groupId=%d".formatted(groupId));
    }

    private void putRecordPage(int userId, int groupId, int recordId, String title, String content, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            recordService.update(title, content, recordId);
            if (groupId == 0) resp.sendRedirect("/records");
            else resp.sendRedirect("/records?groupId=%d".formatted(groupId));
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/templates/records/records.jsp").forward(req, resp);
        }
    }
}
