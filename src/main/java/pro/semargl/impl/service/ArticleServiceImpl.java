package pro.semargl.impl.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pro.semargl.api.dao.ArticleDao;
import pro.semargl.api.service.ArticleService;
import pro.semargl.api.service.MeasurementUnitService;
import pro.semargl.exception.DaoException;
import pro.semargl.exception.ServiceException;
import pro.semargl.model.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    private static final Logger LOGGER = Logger.getLogger(ArticleServiceImpl.class);
    private MeasurementUnitService measurementUnitService;
    private ArticleDao articleDao;

    public ArticleServiceImpl(MeasurementUnitService measurementUnitService, ArticleDao articleDao) {
        this.measurementUnitService = measurementUnitService;
        this.articleDao = articleDao;
    }

    @Override
    public List<Article> findAll() {
        LOGGER.debug("call findAll()");
        return articleDao.findAll();
    }

    @Override
    public long save(Article article) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void saveAll(List<Article> entityList) throws ServiceException {
        LOGGER.debug("call saveAll(" + entityList + ")");
//        measurementUnitService.saveAll(entityList.stream()
//                .map(article -> article.getMeasurementUnit())
//                .collect(Collectors.toList()));
        try {
            articleDao.saveAll(mergeData(findAll(), entityList));
        } catch (DaoException e) {
            LOGGER.error("Exception while store entityList: ", e);
            throw new ServiceException("Exception while store entityList:", e);
        }
    }

    private List<Article> mergeData(List<Article> measurementUnitListDB, List<Article> entityList) {
        LOGGER.debug("call mergeData(" + measurementUnitListDB + "," + entityList + ")");
        List<Article> resultList = new ArrayList<>();
        List<Article> entityListCopy = new ArrayList<>();
        resultList.addAll(measurementUnitListDB);
        entityListCopy.addAll(entityList);
        //add new values from entityList to resultList
        for (int i = 0; i < entityListCopy.size(); i++) {
            Article article = entityListCopy.get(i);
            if (!resultList.contains(article)) {
                resultList.add(article);
            }
        }

        //update exist objects if name is same
        Map<String, Article> articleMap = resultList
                .stream().collect(Collectors.toMap(Article::getTitle, Function.identity()));
        entityListCopy.forEach(article -> {
            String articleTitle = article.getTitle();
            if (articleMap.containsKey(articleTitle)) {
                Article updatableArticle = articleMap.get(articleTitle);
                updatableArticle.setDescription(article.getDescription());
                updatableArticle.setMeasurementUnit(article.getMeasurementUnit());
                updatableArticle.setWeight(article.getWeight());
            }
        });
        resultList.clear();
        resultList.addAll(articleMap.values());
        return resultList;
    }
}
