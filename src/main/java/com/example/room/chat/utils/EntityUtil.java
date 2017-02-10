package com.example.room.chat.utils;

import com.example.room.chat.reference.errors.core.NoEntityWithSuchIdCustomException;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

/**
 * Created by igorek2312 on 26.01.17.
 */
public class EntityUtil {
    private EntityUtil() {

    }

    public static <T, ID extends Serializable> T findOneOrThrowNotFound(
            CrudRepository<T, ID> repository,
            ID id,
            Class<T> repositoryType
    ) {
        T entity = repository.findOne(id);
        if (entity != null) return entity;

        NoEntityWithSuchIdCustomException exception = new NoEntityWithSuchIdCustomException();
        exception.setDescriptionArgs(repositoryType.getName(), id);
        throw exception;
    }

}
