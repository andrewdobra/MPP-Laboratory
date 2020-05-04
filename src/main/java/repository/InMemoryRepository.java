package repository;

import domain.BaseEntity;
import domain.validators.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    protected Map<ID, T> entities;

    public InMemoryRepository() {
        entities = new HashMap<>();
    }

    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        return entities.values().stream().collect(Collectors.toSet());
    }

    @Override
    public Optional<T> save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null.");
        }
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null.");
        }
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
