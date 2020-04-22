package ru.itis.shop.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<ID, E> {
    Optional<E> find(ID id);
    List<E> findAll();
    void save(E entity);
    void delete(ID id);
}

