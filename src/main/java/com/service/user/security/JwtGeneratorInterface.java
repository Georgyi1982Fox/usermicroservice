package com.service.user.security;

import com.service.user.persistence.model.User;
import java.util.Map;
public interface JwtGeneratorInterface{
    Map<String, String> generateToken(User user);
}
