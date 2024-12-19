package ru.itis.tjmoney.services.interfaces;

import ru.itis.tjmoney.dto.ArticleDTO;

import java.util.List;

public interface IArticleService {
    List<ArticleDTO> getAllArticles();
}
