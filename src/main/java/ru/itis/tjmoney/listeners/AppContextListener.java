package ru.itis.tjmoney.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.tjmoney.dao.*;
import ru.itis.tjmoney.dao.interfaces.*;
import ru.itis.tjmoney.services.*;
import ru.itis.tjmoney.services.interfaces.*;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        IArticleDAO articleDAO = new ArticleDAO();
        IArticleService articleService = new ArticleService(articleDAO);
        sce.getServletContext().setAttribute("articleService", articleService);

        IUserDAO userDAO = new UserDAO();
        IAvatarDAO avatarDAO = new AvatarDAO();
        IGroupDAO groupDAO = new GroupDAO();
        IGroupMemberDAO groupMemberDAO = new GroupMemberDAO();
        IRegistrationService registrationService = new RegistrationService(userDAO);
        ILoginService loginService = new LoginService(userDAO);
        IUserService userService = new UserService(userDAO, avatarDAO, groupMemberDAO, groupDAO);
        sce.getServletContext().setAttribute("registrationService", registrationService);
        sce.getServletContext().setAttribute("loginService", loginService);
        sce.getServletContext().setAttribute("userService", userService);

        ITransactionDAO transactionDAO = new TransactionDAO();
        ITransactionService transactionService = new TransactionService(transactionDAO, userDAO);
        sce.getServletContext().setAttribute("transactionService", transactionService);

        IApplicationDAO applicationDAO = new ApplicationDAO();
        IGroupService groupService = new GroupService(userDAO, groupDAO, groupMemberDAO, applicationDAO);
        IGroupMemberService groupMemberService = new GroupMemberService(groupMemberDAO, applicationDAO, userDAO);
        sce.getServletContext().setAttribute("groupService", groupService);
        sce.getServletContext().setAttribute("groupMemberService", groupMemberService);

        IApplicationService applicationService = new ApplicationService(applicationDAO, groupDAO, userDAO);
        sce.getServletContext().setAttribute("applicationService", applicationService);

        IGoalDAO goalDAO = new GoalDAO();
        IGoalService goalService = new GoalService(goalDAO);
        sce.getServletContext().setAttribute("goalService", goalService);

        IReminderDAO reminderDAO = new ReminderDAO();
        IReminderService reminderService = new ReminderService(reminderDAO);
        sce.getServletContext().setAttribute("reminderService", reminderService);

        IRecordDAO recordDAO = new RecordDAO();
        IRecordService recordService = new RecordService(recordDAO);
        sce.getServletContext().setAttribute("recordService", recordService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
