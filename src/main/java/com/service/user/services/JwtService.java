package com.service.user.services;

import com.service.user.config.jwtconfig.JwtSecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class JwtService{
    private final JwtSecretKey jwtSecretKey;
    public JwtService(JwtSecretKey jwtSecretKey){
        this.jwtSecretKey = jwtSecretKey;
    }
    public String generateToken(String userName){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,userName);
    }
    private String createToken(Map<String, Object> claims, String userName){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(jwtSecretKey.getSignKey(), SignatureAlgorithm.HS256).compact();
    }
}
