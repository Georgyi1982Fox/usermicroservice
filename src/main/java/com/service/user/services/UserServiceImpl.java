package com.service.user.services;

import com.service.user.exceptions.MyResourceNotFoundException;
import com.service.user.interfaces.IService;
import com.service.user.interfaces.IUser;
import com.service.user.persistence.dao.UserJpaDao;
import com.service.user.persistence.model.User;
import com.service.user.utils.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class UserServiceImpl implements IService<User>, IUser{
    @Autowired
    UserJpaDao repo;
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll(){
      return repo.findAll();
    }
    @Override
    public User create(final User resource){
        Preconditions.checkIfResourceExists(resource);
        final User persistentUser =  repo.save(resource);
        return persistentUser;
    }
    @Override
    public void update(final long id){
        Optional<User> user = repo.findById(id);
        Preconditions.checkEntityExists(user);
        repo.save(user.get());
    }
    @Override
    public User findOne(long id){
        final Optional<User> user = repo.findById(id);
        Preconditions.checkEntityExists(user.get().id);
        return user.get();
    }
    @Override
    public void delete(final long id){
        final Optional<User> user = repo.findById(id);
        Preconditions.checkEntityExists(user);
        repo.delete(user.get());
    }
    @Override
    public User getUserByNameAndPassword(String name, String password) throws MyResourceNotFoundException{
        User user = repo.findByNameAndPassword(name, password);
        if(user == null){
            throw new  MyResourceNotFoundException("Invalid id and password");
        }
        return user;
    }
}
