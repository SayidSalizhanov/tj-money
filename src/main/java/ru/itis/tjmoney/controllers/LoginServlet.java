package ru.itis.tjmoney.controllers;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.tjmoney.exceptions.LoginException;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.services.LoginService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private LoginService loginService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        loginService = (LoginService) getServletContext().getAttribute("loginService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("templates/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = loginService.login(email, password);

            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("login", user.getEmail());
            session.setAttribute("username", user.getUsername());

            resp.sendRedirect(req.getContextPath() + "/mainPage");
        } catch (LoginException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("templates/login.jsp").forward(req, resp);
        }
    }
}
