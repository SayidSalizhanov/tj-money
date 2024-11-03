package ru.itis.tjmoney.dao;

import ru.itis.tjmoney.exceptions.DaoException;
import ru.itis.tjmoney.models.Article;
import ru.itis.tjmoney.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {
    private static final String FIND_ALL_SQL = "SELECT * FROM Articles";

    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
            while (resultSet.next()) {
                articles.add(
                        new Article(
                                resultSet.getInt("id"),
                                resultSet.getString("title"),
                                resultSet.getString("content"),
                                resultSet.getString("author"),
                                resultSet.getTimestamp("published_at").toLocalDateTime()
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return articles;
    }
}
