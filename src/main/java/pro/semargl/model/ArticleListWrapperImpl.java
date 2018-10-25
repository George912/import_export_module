package pro.semargl.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.stereotype.Component;
import pro.semargl.api.model.ArticleListWrapper;

import java.util.List;

@Component("articleListWrapper")
@JacksonXmlRootElement(localName = "articles")
public class ArticleListWrapperImpl implements ArticleListWrapper {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "article")
    private List<Article> articleList;

    @Override
    public List<Article> getArticleList() {
        return articleList;
    }

    @Override
    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
