package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.ArticleDAO;
import ru.itis.tjmoney.dto.ArticleDTO;
import ru.itis.tjmoney.dto.TransactionDTO;
import ru.itis.tjmoney.models.Article;
import ru.itis.tjmoney.services.interfaces.IArticleService;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ArticleService implements IArticleService {
    private final ArticleDAO articleDAO;

    public ArticleService(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public List<ArticleDTO> getAllArticles() {
        return articleDAO.findAll().stream()
                .map(a -> new ArticleDTO(
                        a.getTitle(),
                        a.getContent(),
                        a.getAuthor(),
                        a.getPublishedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ))
                .toList();
    }
}
