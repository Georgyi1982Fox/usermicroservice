package com.service.user.utils;

import com.service.user.exceptions.MyBadRequestException;
import com.service.user.exceptions.MyEntityNotFoundException;
import com.service.user.exceptions.MyResourceNotFoundException;
public final class Preconditions{
    private Preconditions(){
        throw new AssertionError();
    }
    public static <T> T checkEntityExists(final T entity){
        if (entity == null) {
            throw new MyEntityNotFoundException();
        }
        return entity;
    }
    public static void checkEntityExists(final boolean entityExists){
        if (!entityExists) {
            throw new MyEntityNotFoundException();
        }
    }
    public static void checkOKArgument(final boolean okArgument){
        if (!okArgument) {
            throw new MyBadRequestException();
        }
    }
    public static <T> T checkIfResourceExists(final T resource){
        if (resource == null) {
            throw new MyResourceNotFoundException();
        }
        return resource;
    }
}
