package com.service.user.config.security;

import com.service.user.persistence.dao.UserJpaDao;
import com.service.user.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;
@Component
public class UserDetailServiceInfo implements UserDetailsService{
    @Autowired
    private UserJpaDao userJpaDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User> userInfo = userJpaDao.findByName(username);
        return userInfo.map(UserDetailsInfo::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}
