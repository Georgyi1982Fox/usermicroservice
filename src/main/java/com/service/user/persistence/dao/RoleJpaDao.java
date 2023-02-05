package com.service.user.persistence.dao;

import com.service.user.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface RoleJpaDao extends JpaRepository<Role, Long>{
}
