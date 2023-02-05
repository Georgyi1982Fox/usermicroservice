package com.service.user.config.jwtconfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Configuration;
import java.util.function.Function;
@Configuration
public class JwtExtractUserName{
    private  JwtSecretKey jwtSecretKey;
    public JwtExtractUserName(JwtSecretKey jwtSecretKey){
        this.jwtSecretKey = jwtSecretKey;
    }
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(jwtSecretKey.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
