package ru.itis.tjmoney.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.tjmoney.dao.ArticleDAO;
import ru.itis.tjmoney.dao.TransactionDAO;
import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.services.*;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ArticleDAO articleDAO = new ArticleDAO();
        ArticleService articleService = new ArticleService(articleDAO);
        sce.getServletContext().setAttribute("articleService", articleService);

        UserDAO userDAO = new UserDAO();
        RegistrationService registrationService = new RegistrationService(userDAO);
        LoginService loginService = new LoginService(userDAO);
        UserService userService = new UserService(userDAO);
        sce.getServletContext().setAttribute("registrationService", registrationService);
        sce.getServletContext().setAttribute("loginService", loginService);
        sce.getServletContext().setAttribute("userService", userService);

        TransactionDAO transactionDAO = new TransactionDAO();
        TransactionService transactionService = new TransactionService(transactionDAO);
        sce.getServletContext().setAttribute("transactionService", transactionService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
