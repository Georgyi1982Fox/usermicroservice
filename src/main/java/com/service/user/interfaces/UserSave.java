package com.service.user.interfaces;

import com.service.user.dto.UserRequest;
import com.service.user.persistence.model.User;
public interface UserSave{
    User save(UserRequest request);
    static User userToEntity(UserRequest userRequest){
        User user = User.builder()
                .name(userRequest.getUsername())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .roles(userRequest.getRoles())
                .build();
        return user;
    }

}
