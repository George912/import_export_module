package pro.semargl.impl.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.semargl.api.dao.MeasurementUnitDao;
import pro.semargl.api.service.MeasurementUnitService;
import pro.semargl.exception.DaoException;
import pro.semargl.exception.ServiceException;
import pro.semargl.model.MeasurementUnit;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service("measurementUnitService")
public class MeasurementUnitServiceImpl implements MeasurementUnitService {
    private static final Logger LOGGER = Logger.getLogger(MeasurementUnitServiceImpl.class);
    private MeasurementUnitDao measurementUnitDao;
    @Value("${import.batchSize}")
    private int batchSize;

    public MeasurementUnitServiceImpl(MeasurementUnitDao measurementUnitDao) {
        this.measurementUnitDao = measurementUnitDao;
    }

    @Override
    public List<MeasurementUnit> findAll() {
        LOGGER.debug("call findAll()");
        return measurementUnitDao.findAll();
    }

    @Override
    public void saveAll(Set<MeasurementUnit> entitySet) throws ServiceException {
        LOGGER.debug("call saveAll(" + entitySet + ")");
        List<MeasurementUnit> measurementUnitListDB = this.findAll();
        try {
            Collection<List<MeasurementUnit>> listCollection = partition(entitySet.stream().collect(Collectors.toList())
                    , batchSize);
            for (List<MeasurementUnit> measurementUnitList : listCollection) {
                Set<MeasurementUnit> measurementUnitSet = measurementUnitList.stream().collect(Collectors.toSet());
                measurementUnitDao.saveAll(measurementUnitSet);
            }
        } catch (DaoException e) {
            LOGGER.error("Exception while store entityList: ", e);
            throw new ServiceException("Exception while store entityList:", e);
        }
    }

    private <T> Collection<List<T>> partition(List<T> list, int size) {
        final AtomicInteger counter = new AtomicInteger(0);

        return list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size))
                .values();
    }
}
