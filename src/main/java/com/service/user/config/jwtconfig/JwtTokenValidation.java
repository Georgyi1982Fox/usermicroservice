package com.service.user.config.jwtconfig;

import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;
@Configuration
public class JwtTokenValidation{
    private final JwtExtractUserName extractUserNameConfig;
    public JwtTokenValidation(JwtExtractUserName extractUserNameConfig){
        this.extractUserNameConfig = extractUserNameConfig;
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUserNameConfig.extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractUserNameConfig.extractClaim(token, Claims::getExpiration);
    }
}
