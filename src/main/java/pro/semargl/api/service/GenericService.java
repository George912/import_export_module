package pro.semargl.api.service;

import pro.semargl.exception.ServiceException;

import java.util.List;
import java.util.Set;

/**
 * Base service contain common operations for all entities
 *
 * @param <T>
 */
public interface GenericService<T> {
    List<T> findAll();

    void saveAll(Set<T> entitySet) throws ServiceException;
}
