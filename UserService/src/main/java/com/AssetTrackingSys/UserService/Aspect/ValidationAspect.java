package com.AssetTrackingSys.UserService.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.AssetTrackingSys.UserService.Exception.ValidationException;
import com.AssetTrackingSys.UserService.DTO.LoginRequest;

@Aspect
@Component
public class ValidationAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.AssetTrackingSys.UserService.Auth.AuthController.login(..)) && args(loginRequest,..)")
    public void validateLoginRequest(JoinPoint joinPoint, LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new ValidationException("Login request cannot be null");
        }

        if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username cannot be empty");
        }

        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password cannot be empty");
        }
    }

    @Before("@annotation(javax.validation.Valid)")
    public void validateRequest(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            logger.error("Request validation failed: No arguments provided");
            throw new IllegalArgumentException("Request body is required");
        }
        
        for (Object arg : args) {
            if (arg == null) {
                logger.error("Request validation failed: Null argument detected");
                throw new IllegalArgumentException("Request parameters cannot be null");
            }
        }
    }
} 