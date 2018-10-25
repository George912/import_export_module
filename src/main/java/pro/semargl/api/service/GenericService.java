package pro.semargl.api.service;

import pro.semargl.exception.ServiceException;

import java.util.List;

/**
 * Base service contain common operations for all entities
 *
 * @param <T>
 */
public interface GenericService<T> {
    List<T> findAll();

    long save(T entity);

    void saveAll(List<T> entityList) throws ServiceException;
}
