package com.AssetTrackingSys.UserService.User;


import com.AssetTrackingSys.UserService.Auth.AuthService;
import com.AssetTrackingSys.UserService.DTO.UserDTO;
import com.AssetTrackingSys.UserService.Exceptions.NotFoundException;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final AuthService authService;

    @Autowired
    public UserService(AuthService authService) {
        this.authService = authService;
    }


    public User getUserById(Long userId){

        User userExample = new User();

        userExample.setId(userId);

        Optional<User> userRecord = userRepo.findOne(Example.of(userExample));

        if(userRecord.isPresent()) return userRecord.get();


        throw new NotFoundException("No user found with that id (" + userId + ")");
    }

    public List<User> getAllUsers(Long userId, Integer role_id){
        return userRepo.findAllExceptAdminAndId(userId, role_id);
    }

    public void createUser(User user){
        authService.register(user);
    }

    public void updateUser(User newUser){

        User userExample = new User();

        userExample.setId(newUser.getId());

        Optional<User> userRecord = userRepo.findOne(Example.of(userExample));

        if(userRecord.isPresent()) {
            if (newUser.getPassword() != null) userRecord.get().setPassword(passwordEncoder.encode(newUser.getPassword()));

            userRecord.get().setName(newUser.getName());
        }
        else
            throw new NotFoundException("No user found with that id (" + newUser.getId() + ")");


        userRepo.save(userRecord.get());
    }

    public Map<Long, UserDTO> findUsersByIds(Set<Long> ids) {
        return userRepo.findAllById(ids).stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getRole()))
                .collect(Collectors.toMap(UserDTO::getId, user -> user));
    }

}
