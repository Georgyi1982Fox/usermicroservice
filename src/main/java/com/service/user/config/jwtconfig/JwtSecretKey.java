package com.service.user.config.jwtconfig;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import java.security.Key;
@Configuration
public class JwtSecretKey{
    private JwtConfig jwtConfig;
    public JwtSecretKey(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }
    public Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
