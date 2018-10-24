package pro.semargl.api.dao;

import java.util.List;

/**
 * Base dao contain common operations for all entities
 * @param <T>
 */
public interface GenericDao<T> {
    long save(T t);
    List<T> findAll();
}
