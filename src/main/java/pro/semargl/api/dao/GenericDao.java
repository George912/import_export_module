package pro.semargl.api.dao;

import pro.semargl.exception.DaoException;

import java.util.List;

/**
 * Base dao contain common operations for all entities
 *
 * @param <T>
 */
public interface GenericDao<T> {
    List<T> findAll();

    long save(T entity);

    void saveAll(List<T> entityList) throws DaoException;
}
