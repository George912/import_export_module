package pro.semargl.impl.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pro.semargl.api.dao.MeasurementUnitDao;
import pro.semargl.model.MeasurementUnit;

import java.util.List;

@Transactional
@Repository("measurementUnitDao")
public class MeasurementUnitDaoImpl implements MeasurementUnitDao {
    private static final Logger LOGGER = Logger.getLogger(MeasurementUnitDaoImpl.class);
    private SessionFactory sessionFactory;
    @Value("${import.batchSize}")
    private int batchSize;

    public MeasurementUnitDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<MeasurementUnit> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from MeasurementUnit mu")
                .list();
    }

    @Override
    public long save(MeasurementUnit measurementUnit) {
        LOGGER.debug("call save(" + measurementUnit + ")");
        sessionFactory.getCurrentSession().saveOrUpdate(measurementUnit);
        LOGGER.debug("Measurement unit saved with id: " + measurementUnit.getId());
        return measurementUnit.getId();
    }

    @Override
    public void saveAll(List<MeasurementUnit> entityList) {
        LOGGER.debug("call saveAll(" + entityList + ")");
        Session session = sessionFactory.getCurrentSession();
        for (int i = 0; i <= entityList.size(); i++) {
            if ((i > 0) && (i % batchSize == 0)) {
                session.flush();
                session.clear();
                if (i == entityList.size()) {
                    continue;
                }
            }
            session.saveOrUpdate(entityList.get(i));
        }
    }
}
