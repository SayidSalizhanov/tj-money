package ru.itis.tjmoney.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.tjmoney.dao.ArticleDAO;
import ru.itis.tjmoney.dao.UserDAO;
import ru.itis.tjmoney.services.ArticleService;
import ru.itis.tjmoney.services.RegistrationService;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ArticleDAO articleDAO = new ArticleDAO();
        ArticleService articleService = new ArticleService(articleDAO);
        sce.getServletContext().setAttribute("articleService", articleService);

        UserDAO userDAO = new UserDAO();
        RegistrationService registrationService = new RegistrationService(userDAO);
        sce.getServletContext().setAttribute("registrationService", registrationService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
