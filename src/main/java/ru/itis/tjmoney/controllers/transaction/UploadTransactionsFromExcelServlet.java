package ru.itis.tjmoney.controllers.transaction;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.itis.tjmoney.exceptions.ExcelParseException;
import ru.itis.tjmoney.services.TransactionService;

import java.io.IOException;

@WebServlet("/transactions/new/uploadTransactions")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class UploadTransactionsFromExcelServlet extends HttpServlet {
    private TransactionService transactionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        transactionService = (TransactionService) getServletContext().getAttribute("transactionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/templates/transactions/uploadXlsTransactions.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");
        String groupIdStr = req.getParameter("groupId");
        int groupId = groupIdStr == null ? 0 : Integer.parseInt(groupIdStr);

        Part filePart = req.getPart("file");

        try {
            transactionService.parseExcelToTransactions(filePart, userId, groupId);
        } catch (ExcelParseException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/templates/transactions/uploadXlsTransactions.jsp").forward(req, resp);
        }

        if (groupId == 0) resp.sendRedirect("/transactions");
        else resp.sendRedirect("/transactions?groupId=%d".formatted(groupId));
    }
}
