package pro.semargl.api.model;

import pro.semargl.model.Article;

import java.util.List;

public interface ArticleListWrapper {
    List<Article> getArticleList();

    void setArticleList(List<Article> articleList);
}
