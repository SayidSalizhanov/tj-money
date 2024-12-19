package ru.itis.tjmoney.controllers.user;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.tjmoney.services.TransactionService;
import ru.itis.tjmoney.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/user")
public class UserProfileServlet extends HttpServlet {
    private UserService userService;
    private TransactionService transactionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
        transactionService = (TransactionService) getServletContext().getAttribute("transactionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = (Integer) req.getSession().getAttribute("userId");

        List<Map<String, Integer>> transactionsGenerals;

        String period = req.getParameter("period");
        if (period == null || period.isEmpty()) {
            transactionsGenerals = transactionService.getUserTransactionsGenerals(userId, "all");
        }
        else {
            transactionsGenerals = transactionService.getUserTransactionsGenerals(userId, period);
        }

        req.setAttribute("user", userService.getUserById(userId));
        req.setAttribute("urlPhoto", userService.getPhotoUrl(userId));

        req.setAttribute("income", transactionsGenerals.get(0).values().stream().mapToInt(Integer::intValue).sum());
        req.setAttribute("expense", transactionsGenerals.get(1).values().stream().mapToInt(Integer::intValue).sum());

        for (Map.Entry<String, Integer> entry : transactionsGenerals.get(0).entrySet()) {
            if (entry.getKey().equalsIgnoreCase("Другое")) req.setAttribute("Другие_доходы", entry.getValue());
            else req.setAttribute(entry.getKey().replaceAll("\\s+", "_"), entry.getValue());
        }
        for (Map.Entry<String, Integer> entry : transactionsGenerals.get(1).entrySet()) {
            if (entry.getKey().equalsIgnoreCase("Другое")) req.setAttribute("Другие_расходы", entry.getValue());
            else req.setAttribute(entry.getKey().replaceAll("\\s+", "_"), entry.getValue());
        }

        req.setAttribute("userId", userId);
        req.getRequestDispatcher("/templates/users/userProfile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect("/mainPage");
    }
}
