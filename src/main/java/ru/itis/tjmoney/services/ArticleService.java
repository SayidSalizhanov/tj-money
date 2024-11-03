package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.ArticleDAO;
import ru.itis.tjmoney.models.Article;

import java.util.List;

public class ArticleService {
    private final ArticleDAO articleDAO;

    public ArticleService(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public List<Article> getAllArticles() {
        return articleDAO.findAll();
    }
}
