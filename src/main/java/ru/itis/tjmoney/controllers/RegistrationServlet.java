package ru.itis.tjmoney.controllers;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.tjmoney.exceptions.RegistrationException;
import ru.itis.tjmoney.models.User;
import ru.itis.tjmoney.services.RegistrationService;

import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private RegistrationService registrationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        registrationService = (RegistrationService) getServletContext().getAttribute("registrationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("templates/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        try {
            User user = registrationService.register(username, email, password, confirmPassword);

            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("login", user.getEmail());
            session.setAttribute("username", user.getUsername());

            resp.sendRedirect(req.getContextPath() + "/mainPage");
        } catch (RegistrationException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/templates/register.jsp").forward(req, resp);
        }
    }
}
