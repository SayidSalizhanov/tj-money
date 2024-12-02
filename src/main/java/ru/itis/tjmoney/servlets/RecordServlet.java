package ru.itis.tjmoney.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.exceptions.UpdateException;
import ru.itis.tjmoney.services.RecordService;

import java.io.IOException;

@WebServlet("/records/*")
public class RecordServlet extends HttpServlet {
    private RecordService recordService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        recordService = (RecordService) getServletContext().getAttribute("recordService");
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
            getRecordsRequest(userId, groupId, req, resp);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String action = pathParts[1];

        if (action.equals("new")) {
            getRecordNew(userId, groupId, req, resp);
        } else {
            int recordId;
            try {
                recordId = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
                return;
            }

            getRecordPage(recordId, req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");
        if ("DELETE".equals(method)) {
            doDelete(req, resp);
        } else if ("PUT".equals(method)) {
            doPut(req, resp);
        }

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
            postRecordNew(
                    userId,
                    groupId,
                    req.getParameter("title"),
                    req.getParameter("content"),
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
            int recordId;
            try {
                recordId = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
                return;
            }

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
            int recordId;
            try {
                recordId = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
                return;
            }

            deleteRecordPage(userId, groupId, recordId, req, resp);
        }
    }

    private void getRecordsRequest(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.setAttribute("records", recordService.getUserAndGroupRecords(userId, groupId));
        req.getRequestDispatcher("templates/records/records.jsp").forward(req, resp);
    }

    private void getRecordPage(int recordId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("record", recordService.getRecord(recordId));
        req.setAttribute("recordId", recordId);
        req.getRequestDispatcher("templates/records/record.jsp").forward(req, resp);
    }

    private void getRecordNew(int userId, int groupId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userId", userId);
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("templates/records/newRecord.jsp").forward(req, resp);
    }

    private void postRecordNew(int userId, int groupId, String title, String content, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        recordService.save(userId, groupId, title, content);
        if (groupId == 0) resp.sendRedirect(req.getContextPath() + "/records?" + "userId=" + userId);
        else resp.sendRedirect(req.getContextPath() + "/records?" + "userId=" + userId + "&groupId=" + groupId);
    }

    private void deleteRecordPage(int userId, int groupId, int recordId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        recordService.delete(recordId);
        if (groupId == 0) resp.sendRedirect(req.getContextPath() + "/records?" + "userId=" + userId);
        else resp.sendRedirect(req.getContextPath() + "/records?" + "userId=" + userId + "&groupId=" + groupId);
    }

    private void putRecordPage(int userId, int groupId, int recordId, String title, String content, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            recordService.update(title, content, recordId);
            if (groupId == 0) resp.sendRedirect(req.getContextPath() + "/records?" + "userId=" + userId);
            else resp.sendRedirect(req.getContextPath() + "/records?" + "userId=" + userId + "&groupId=" + groupId);
        } catch (UpdateException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("templates/records/records.jsp").forward(req, resp);
        }
    }
}
