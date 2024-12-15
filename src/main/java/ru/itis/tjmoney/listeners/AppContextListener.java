package ru.itis.tjmoney.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.tjmoney.dao.*;
import ru.itis.tjmoney.services.*;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ArticleDAO articleDAO = new ArticleDAO();
        ArticleService articleService = new ArticleService(articleDAO);
        sce.getServletContext().setAttribute("articleService", articleService);

        UserDAO userDAO = new UserDAO();
        AvatarDAO avatarDAO = new AvatarDAO();
        RegistrationService registrationService = new RegistrationService(userDAO);
        LoginService loginService = new LoginService(userDAO);
        UserService userService = new UserService(userDAO, avatarDAO);
        sce.getServletContext().setAttribute("registrationService", registrationService);
        sce.getServletContext().setAttribute("loginService", loginService);
        sce.getServletContext().setAttribute("userService", userService);

        TransactionDAO transactionDAO = new TransactionDAO();
        TransactionService transactionService = new TransactionService(transactionDAO, userDAO);
        sce.getServletContext().setAttribute("transactionService", transactionService);

        GroupDAO groupDAO = new GroupDAO();
        GroupMemberDAO groupMemberDAO = new GroupMemberDAO();
        ApplicationDAO applicationDAO = new ApplicationDAO();
        GroupService groupService = new GroupService(userDAO, groupDAO, groupMemberDAO, applicationDAO);
        GroupMemberService groupMemberService = new GroupMemberService(groupMemberDAO, applicationDAO, userDAO);
        sce.getServletContext().setAttribute("groupService", groupService);
        sce.getServletContext().setAttribute("groupMemberService", groupMemberService);

        ApplicationService applicationService = new ApplicationService(applicationDAO, groupDAO, userDAO);
        sce.getServletContext().setAttribute("applicationService", applicationService);

        GoalDAO goalDAO = new GoalDAO();
        GoalService goalService = new GoalService(goalDAO);
        sce.getServletContext().setAttribute("goalService", goalService);

        ReminderDAO reminderDAO = new ReminderDAO();
        ReminderService reminderService = new ReminderService(reminderDAO);
        sce.getServletContext().setAttribute("reminderService", reminderService);

        RecordDAO recordDAO = new RecordDAO();
        RecordService recordService = new RecordService(recordDAO);
        sce.getServletContext().setAttribute("recordService", recordService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
