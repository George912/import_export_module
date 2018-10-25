package pro.semargl.api.model;

import pro.semargl.model.Article;

import java.util.List;

public interface ArticleListWrapper {
    public List<Article> getArticleList();

    public void setArticleList(List<Article> articleList);
}
