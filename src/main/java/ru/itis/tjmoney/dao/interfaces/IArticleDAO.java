package ru.itis.tjmoney.dao.interfaces;

import ru.itis.tjmoney.models.Article;

import java.util.List;

public interface IArticleDAO {
    List<Article> findAll();
}
