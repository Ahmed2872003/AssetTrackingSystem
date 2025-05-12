package com.AssetTrackingSys.UserService.Auth;


import com.AssetTrackingSys.UserService.DTO.LoginRequestDTO;
import com.AssetTrackingSys.UserService.DTO.RegisterRequestDTO;
import com.AssetTrackingSys.UserService.User.User;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterRequestDTO requestBody){
        User user = new User();

        user.setName(requestBody.name);
        user.setRole(requestBody.role);
        user.setPassword(requestBody.password);

        authService.register(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> login(@RequestBody @Valid LoginRequestDTO reqeustBody){

        HttpStatus status;
        User user = new User();
        Map <String, Object> responseBody = new HashMap<>() ;

        user.setName(reqeustBody.name);
        user.setPassword(reqeustBody.password);

        return authService.login(user);

    }

}
