package com.service.user.services;

import com.service.user.exceptions.MyResourceNotFoundException;
import com.service.user.interfaces.IService;
import com.service.user.interfaces.IUser;
import com.service.user.persistence.dao.UserJpaDao;
import com.service.user.persistence.model.User;
import com.service.user.interfaces.UserSave;
import com.service.user.dto.UserRequest;
import com.service.user.utils.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class UserServiceImpl implements IService {
    @Autowired
    UserJpaDao repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    public User save(UserRequest userRequest) {

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User persistedUser = UserSave.userToEntity(userRequest);
        return repo.save(persistedUser);
    }

    @Override
    public void update(final long id) {
        Optional<User> user = repo.findById(id);
        Preconditions.checkEntityExists(user);
        repo.save(user.get());
    }

    @Override
    public User findOne(long id) {
        final Optional<User> user = repo.findById(id);
        Preconditions.checkEntityExists(user.get());
        return user.get();
    }

    @Override
    @Transactional
    public void delete(final long id) {
        final Optional<User> user = repo.findById(id);
        Preconditions.checkEntityExists(user);
        repo.delete(user.get());
    }
}
