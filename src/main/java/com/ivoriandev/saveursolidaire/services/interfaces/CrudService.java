package com.ivoriandev.saveursolidaire.services.interfaces;

import java.util.List;

public interface CrudService<T> {
    T create(T t);

    List<T> all();

    T read(Integer id);

    T update(T t);

    void delete(Integer id);
}
