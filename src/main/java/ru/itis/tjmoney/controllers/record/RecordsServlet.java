package ru.itis.tjmoney.controllers.record;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.RecordService;

import java.io.IOException;

@WebServlet("/records")
public class RecordsServlet extends HttpServlet {
    private RecordService recordService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        recordService = (RecordService) getServletContext().getAttribute("recordService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        getRecordsRequest(
                Integer.parseInt(req.getParameter("userId")),
                groupId,
                req, resp
        );
    }

    private void getRecordsRequest(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.setAttribute("records", recordService.getUserAndGroupRecords(userId, groupId));
        req.getRequestDispatcher("templates/records/records.jsp").forward(req, resp);
    }
}
