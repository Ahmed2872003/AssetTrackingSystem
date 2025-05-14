package com.AssetTrackingSys.UserService.Auth;

import com.AssetTrackingSys.UserService.DTO.RegisterRequestDTO;
import com.AssetTrackingSys.UserService.Exceptions.NotFoundException;
import com.AssetTrackingSys.UserService.Exceptions.UnauthorizedException;
import com.AssetTrackingSys.UserService.Security.Utils.JWTUtil;
import com.AssetTrackingSys.UserService.User.User;
import com.AssetTrackingSys.UserService.User.UserRepo;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.expiration}")
    private Long EXPIRATION_TIME_MILLI;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public  void register(User user) {

        Integer role_id = User.Roles.getIdFromRole(user.getRole());
        //        Hashing password
        String hashedPassword = passwordEncoder.encode(user.getPassword());

        //        Set user data
        user.setRole_id(role_id);
        user.setPassword(hashedPassword);

        userRepo.save(user);
    }

    public Map<String, Object> login(User user){

        User userExample = new User();

        userExample.setName(user.getName());

        Optional<User> userRecord = userRepo.findOne(Example.of(userExample));

        if(userRecord.isPresent()){
            String hashedpassword = passwordEncoder.encode(user.getPassword());

            if(passwordEncoder.matches(user.getPassword(), userRecord.get().getPassword())){
                JWTUtil jwt = new JWTUtil(JWT_SECRET, EXPIRATION_TIME_MILLI);

                String token = jwt.generateToken(userRecord.get());

                Map<String, Object> userIdentity = new HashMap<>();

                userIdentity.put("token", token);
                userIdentity.put("id", userRecord.get().getId());

                return userIdentity;
            }
            throw new UnauthorizedException("Wrong password");
        }

        throw new NotFoundException("username doesn't exist");

    }

}
