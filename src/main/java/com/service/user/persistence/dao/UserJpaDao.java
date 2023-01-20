package com.service.user.persistence.dao;

import com.service.user.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface UserJpaDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{
    User findByNameAndPassword(String name, String password);
}
