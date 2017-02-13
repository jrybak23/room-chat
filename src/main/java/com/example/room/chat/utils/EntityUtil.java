package com.example.room.chat.utils;

import com.example.room.chat.reference.errors.core.NoEntityWithSuchIdCustomException;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

/**
 * A util class for generic operations with repositories.
 * @author Igor Rybak
 */
public class EntityUtil {
    private EntityUtil() {

    }

    /**
     * Retrieve entity using {@link org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)}.
     * else the id and entity type will be passed to exception for error message interpolation.
     *
     * @param repository the repository for entity reprieving
     * @param id         the id of entity
     * @param entityType the Class object corresponding to the entity type
     * @param <T>        the type of entity
     * @param <ID>       the type of id of entity
     * @return the found entity if present
     * @throws NoEntityWithSuchIdCustomException if entity was not found
     */
    public static <T, ID extends Serializable> T findOneOrThrowNotFound(
            CrudRepository<T, ID> repository,
            ID id,
            Class<T> entityType
    ) {
        T entity = repository.findOne(id);
        if (entity != null) return entity;

        NoEntityWithSuchIdCustomException exception = new NoEntityWithSuchIdCustomException();
        exception.setDescriptionArgs(entityType.getName(), id);
        throw exception;
    }

}
