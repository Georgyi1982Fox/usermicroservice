package com.service.user.web;

import com.service.user.exceptions.MyResourceNotFoundException;
import com.service.user.interfaces.IController;
import com.service.user.interfaces.IService;
import com.service.user.interfaces.IUser;
import com.service.user.persistence.model.User;
import com.service.user.security.JwtGeneratorInterface;
import com.service.user.services.UserServiceImpl;
import com.service.user.util.Mappings;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping(value = Mappings.USERS)
public class UserController implements IController<User>{

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private IUser iUserService;
    @Autowired
    private JwtGeneratorInterface jwtGenerator;

    @Override
    @GetMapping
    public List<User> findAll(HttpServletRequest request){
        if (request.getParameterNames().hasMoreElements()){
            throw new MyResourceNotFoundException();
        }
        return getService().findAll();
    }
    @Override
    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createInternal(User resource){
        getService().create(resource);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            if(user.getName() == null || user.getPassword() == null) {
                throw new MyResourceNotFoundException("UserName or Password is Empty");
            }
            User userData = iUserService.getUserByNameAndPassword(user.getName(), user.getPassword());
            if(userData == null){
                throw new MyResourceNotFoundException("UserName or Password is Invalid");
            }
            return new ResponseEntity<>(jwtGenerator.generateToken(user), HttpStatus.OK);
        } catch (MyResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateInternal(@PathVariable("id") final long id){
        getService().update(id);
    }
    @Override
    @GetMapping(value = "/{id}")
    public User findById(@PathVariable("id")long id){
      return (User) getService().findOne(id);
    }
    @Override
    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") long id){
        getService().delete(id);
    }
    public final IService getService(){
        return userService;
    }
}
