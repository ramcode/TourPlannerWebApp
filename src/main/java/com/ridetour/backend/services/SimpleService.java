package com.ridetour.backend.services;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * Created by eyal on 5/21/2016.
 */

class SimpleService
        <T extends Persistable<ID>, ID extends Serializable, R extends CrudRepository<T, ID>> {
    @Autowired
    private R repository;

    protected Iterable<T> oldEntities(Iterable<T> entities) {
        return FluentIterable.from(entities)
                .filter(input -> !Preconditions.checkNotNull(input).isNew()).toList();
    }

    public Iterable<T> all() {
        return repository.findAll();
    }

    Optional<T> findById(ID id) {
        return Optional.fromNullable(repository.findOne(id));
    }

    @Transactional
    T save(T entity) {
        return repository.save(entity);
    }

    @Transactional
    public Iterable<T> save(Iterable<T> entities) {
        return repository.save(Preconditions.checkNotNull(entities));
    }

    protected Iterable<ID> idList(Iterable<T> entities) {
        return FluentIterable.from(entities)
                .transform(new Function<T, ID>() {
                    @Nullable
                    @Override
                    public ID apply(@Nullable T input) {
                        return Preconditions.checkNotNull(input).getId();
                    }
                }).toList();
    }

    R getRepository() {
        return this.repository;
    }
}
