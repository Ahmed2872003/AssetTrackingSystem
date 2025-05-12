package com.AssetTrackingSys.UserService.User;


import com.AssetTrackingSys.UserService.DTO.UpdateUserRequestDTO;
import com.AssetTrackingSys.UserService.DTO.RegisterRequestDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    UserService userService;


    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAllExceptAdminAndId(){

        SecurityContext context = SecurityContextHolder.getContext();

        Authentication auth = context.getAuthentication();

        User user = (User) auth.getPrincipal();

        return userService.getAllUsers(user.getId(), User.Roles.getIdFromRole("ADMIN"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody RegisterRequestDTO requestBody){
        User user = new User();

        user.setName(requestBody.name);
        user.setRole(requestBody.role);
        user.setPassword(requestBody.password);

        userService.createUser(user);
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserById(@PathVariable(value = "id") Long userId, @RequestBody @Valid UpdateUserRequestDTO requestBody){
        User user = new User();

        user.setId(userId);
        user.setName(requestBody.name);
        user.setPassword(requestBody.password);

        userService.updateUser(user);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void udpateUser(@RequestBody @Valid UpdateUserRequestDTO requestBody){
        SecurityContext context = SecurityContextHolder.getContext();

        Authentication auth = context.getAuthentication();

        User user = (User) auth.getPrincipal();

        user.setName(requestBody.name);
        user.setPassword(requestBody.password);

        userService.updateUser(user);
    }


}
