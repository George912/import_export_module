package pro.semargl.impl.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pro.semargl.api.dao.ArticleDao;
import pro.semargl.exception.DaoException;
import pro.semargl.model.Article;

import java.util.List;
import java.util.Set;

@Transactional
@Repository("articleDao")
public class ArticleDaoImpl implements ArticleDao {
    private static final Logger LOGGER = Logger.getLogger(ArticleDaoImpl.class);
    private SessionFactory sessionFactory;
    @Value("${import.batchSize}")
    private int batchSize;

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
    public void saveAll(Set<Article> entitySet) throws DaoException {
        LOGGER.debug("call saveAll(" + entitySet + ")");
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            entitySet.forEach(article ->
                    session.saveOrUpdate(article)
            );
            session.flush();
            session.clear();
        } catch (HibernateException e) {
            LOGGER.error("Exception while store entity set: ", e);
            throw new DaoException("Exception while store entity set: ", e);
        }
    }
}
