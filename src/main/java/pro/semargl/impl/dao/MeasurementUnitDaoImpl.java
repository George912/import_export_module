package pro.semargl.impl.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
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

    public MeasurementUnitDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long save(MeasurementUnit measurementUnit) {
        return 0;
    }

    @Override
    public List<MeasurementUnit> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from MeasurementUnit mu")
                .list();
    }
}
