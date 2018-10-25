package pro.semargl.impl.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pro.semargl.api.dao.ArticleDao;
import pro.semargl.model.Article;

import java.util.List;

@Transactional
@Repository("articleDao")
public class ArticleDaoImpl implements ArticleDao {
    private static final Logger LOGGER = Logger.getLogger(ArticleDaoImpl.class);
    private SessionFactory sessionFactory;
    //todo: load from config.properties
    private int batchSize = 5;

    public ArticleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Article> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Article a")
                .list();
    }

    @Override
    public long save(Article article) {
        LOGGER.debug("call save(" + article + ")");
        sessionFactory.getCurrentSession().saveOrUpdate(article);
        LOGGER.debug("Article saved with id: " + article.getId());
        return article.getId();
    }

    @Override
    public void saveAll(List<Article> entityList) {
        LOGGER.debug("call saveAll(" + entityList + ")");
        Session session = sessionFactory.getCurrentSession();
        for (int i = 0; i <= entityList.size(); i++) {
            if ((i > 0) && (i % batchSize == 0)) {
                session.flush();
                session.clear();
                if(i == entityList.size()){
                    continue;
                }
            }
            session.saveOrUpdate(entityList.get(i));
        }
    }
}
