package com.service.user.interfaces;

import com.service.user.exceptions.MyResourceNotFoundException;
import com.service.user.persistence.model.User;
public interface IUser{
    User findUserByNameAndPassword(String name, String password) throws MyResourceNotFoundException;
}
