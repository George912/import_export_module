package pro.semargl.impl.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pro.semargl.api.dao.MeasurementUnitDao;
import pro.semargl.exception.DaoException;
import pro.semargl.model.MeasurementUnit;

import java.util.List;
import java.util.Set;

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
    public void saveAll(Set<MeasurementUnit> entitySet) throws DaoException {
        LOGGER.debug("call saveAll(" + entitySet + ")");
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            entitySet.forEach(measurementUnit ->
                session.saveOrUpdate(measurementUnit)
            );
            session.flush();
            session.clear();
        } catch (HibernateException e) {
            LOGGER.error("Exception while store entity set: ", e);
            throw new DaoException("Exception while store entity set: ", e);
        }

    }
}
