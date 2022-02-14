package com.galeeva.project.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {

    boolean delete(K id);

    E save(E orderData);

    void update(E orderData);

    Optional<E> findById(K id);

    List<E> findAll();
}
