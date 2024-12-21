package ru.itis.tjmoney.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.flywaydb.core.Flyway;

@WebListener
public class FlywayMigrationListener implements ServletContextListener {
    String PROD_DB_HOST = System.getenv("PROD_DB_HOST");
    String PROD_DB_PORT = System.getenv("PROD_DB_PORT");
    String PROD_DB_PASSWORD = System.getenv("PROD_DB_PASSWORD");
    String PROD_DB_NAME = System.getenv("PROD_DB_NAME");
    String PROD_DB_USERNAME = System.getenv("PROD_DB_USERNAME");

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String url = "jdbc:postgresql://%s:%s/%s".formatted(PROD_DB_HOST, PROD_DB_PORT, PROD_DB_NAME);
        String user = PROD_DB_USERNAME;
        String password = PROD_DB_PASSWORD;

        Flyway flyway = Flyway
                .configure()
                .dataSource(url, user, password)
                .load();
        flyway.migrate();
    }
}
