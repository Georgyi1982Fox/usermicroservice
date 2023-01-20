package com.service.user.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public interface IController<T>{
    List<T> findAll(final HttpServletRequest request);
    void createInternal(final T resource);
    void updateInternal(final long id);
    T findById(final long id);
    void deleteById(final long id);
}
