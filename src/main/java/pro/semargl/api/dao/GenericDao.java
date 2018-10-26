package pro.semargl.api.dao;

import pro.semargl.exception.DaoException;

import java.util.List;
import java.util.Set;

/**
 * Base dao contain common operations for all entities
 *
 * @param <T>
 */
public interface GenericDao<T> {
    List<T> findAll();

    void saveAll(Set<T> entitySet) throws DaoException;
}
