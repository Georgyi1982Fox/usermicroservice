package com.service.user.web;

import com.service.user.dto.AuthRequest;
import com.service.user.exceptions.MyResourceNotFoundException;
import com.service.user.interfaces.IController;
import com.service.user.persistence.model.ErrorObject;
import com.service.user.persistence.model.User;
import com.service.user.dto.UserRequest;
import com.service.user.interfaces.UserSave;
import com.service.user.services.JwtService;
import com.service.user.services.JwtService;
import com.service.user.services.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "user", description = "The User API")
public class UserController implements IController<User>{
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(
            summary = "Find all users",
            description = "Find all users",
            tags = "user")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successful operation",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorObject.class)
                            )
                    )
            }
    )
    @Override
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> findAll(HttpServletRequest request){
        if (request.getParameterNames().hasMoreElements()){
            throw new MyResourceNotFoundException();
        }
        return getService().findAll();
    }

    @Operation(
            summary = "Register new user",
            description = "Register new user",
            tags = "user"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successful operation",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorObject.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User already exists",
                            content = @Content(schema = @Schema(implementation = ErrorObject.class)))
            }
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody UserRequest request) {
        return getService().save(request);
    }


    @Operation(
            summary = "Update user",
            description = "Update user info",
            tags = "user"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(schema = @Schema(implementation = ErrorObject.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorObject.class))
                    )
            }
    )
    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER')")
    public void updateInternal(@PathVariable("id") final long id){
        getService().update(id);
    }

    @Operation(
            summary = "Find user by ID",
            description = "Find user",
            tags = "user"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(schema = @Schema(implementation = ErrorObject.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorObject.class))
                    )
            }
    )
    @Override
    @GetMapping(value = "/{id}")
    public User findById(@PathVariable("id")long id){
      return (User) getService().findOne(id);
    }


    @Operation(
            summary = "Delete a user",
            description = "Delete a user",
            tags = "user"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorObject.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Member not found",
                            content = @Content(schema = @Schema(implementation = ErrorObject.class))
                    )
            }
    )

    @Override
    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") long id){
        getService().delete(id);
    }


    @Operation(
            summary = "Authenticate a new user",
            description = "Authenticate new user",
            tags = "user"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successful authentication operation",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorObject.class)
                            )
                    )
            }
    )
    @PostMapping("/authenticate")
    public String authenticatedAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                        authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        }else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
    public final UserServiceImpl getService(){
        return userService;
    }

}
