package pro.semargl.impl.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.semargl.api.dao.ArticleDao;
import pro.semargl.api.service.ArticleService;
import pro.semargl.api.service.MeasurementUnitService;
import pro.semargl.exception.ServiceException;
import pro.semargl.model.Article;
import pro.semargl.model.MeasurementUnit;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    private static final Logger LOGGER = Logger.getLogger(ArticleServiceImpl.class);
    private MeasurementUnitService measurementUnitService;
    private ArticleDao articleDao;
    @Value("${import.batchSize}")
    private int batchSize;

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
    public void saveAll(Set<Article> entitySet) throws ServiceException {
        LOGGER.debug("call saveAll(" + entitySet + ")");
        try {
            //1
            Set<MeasurementUnit> measurementUnitSet = entitySet.stream().map(Article::getMeasurementUnit)
                    .collect(Collectors.toSet());
            measurementUnitService.saveAll(measurementUnitSet);
            //2
            Set<Article> entitySetToStore = findAll().stream().collect(Collectors.toSet());
            entitySetToStore.addAll(entitySet);

            Collection<List<Article>> listCollection = partition(entitySetToStore.stream().collect(Collectors.toList())
                    , batchSize);
            for (List<Article> articleList : listCollection) {
                Set<Article> articleSet = articleList.stream().collect(Collectors.toSet());
                articleDao.saveAll(articleSet);
            }
        } catch (Exception e) {
            LOGGER.error("Exception while store entitySet: ", e);
            throw new ServiceException("Exception while store entitySet:", e);
        }
    }

    private <T> Collection<List<T>> partition(List<T> list, int size) {
        final AtomicInteger counter = new AtomicInteger(0);

        return list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size))
                .values();
    }
}
