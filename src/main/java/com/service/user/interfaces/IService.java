package com.service.user.interfaces;

import java.util.List;
public interface IService<T>{
    List<T> findAll();
    T findOne(final long id);
    T create(final T resource);
    void update(final long id);
    void delete(final long id);
}
