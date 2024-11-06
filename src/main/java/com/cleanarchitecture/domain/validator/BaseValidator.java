package com.cleanarchitecture.domain.validator;

import com.cleanarchitecture.domain.exception.DomainException;

/**
 * Base validator interface for domain validation
 * @param <T> Type of entity to validate
 */
public interface BaseValidator<T> {
    /**
     * Validates the entity for standard operations
     * @param entity Entity to validate
     * @throws DomainException if validation fails
     */
    void validate(T entity) throws DomainException;
}
