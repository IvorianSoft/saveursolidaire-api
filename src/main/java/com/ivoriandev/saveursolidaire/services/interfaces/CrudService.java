package com.ivoriandev.saveursolidaire.services.interfaces;

import java.util.List;

public interface CrudService<T> {
    default T create(T t) {
        return null;
    }

    List<T> all();

    T read(Integer id);

    default T update(Integer id, T t) {
        return null;
    }

    void delete(Integer id);
}
