package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.interfaces.IArticleDAO;
import ru.itis.tjmoney.dto.ArticleDTO;
import ru.itis.tjmoney.services.interfaces.IArticleService;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ArticleService implements IArticleService {
    private final IArticleDAO articleDAO;

    public ArticleService(IArticleDAO articleDAO) {
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
