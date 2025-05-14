package com.AssetTrackingSys.UserService.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class SecurityAspect {
    private final Logger logger = LoggerFactory.getLogger("SecurityAudit");

    @Before("execution(* com.AssetTrackingSys.UserService.Auth.AuthController.login(..))")
    public void beforeAuthentication(JoinPoint joinPoint) {
        logger.info("Authentication attempt");
    }

    @AfterReturning("execution(* com.AssetTrackingSys.UserService.Auth.AuthController.login(..))")
    public void afterSuccessfulAuthentication(JoinPoint joinPoint) {
        logger.info("Authentication successful");
    }

    @AfterThrowing(pointcut = "execution(* com.AssetTrackingSys.UserService.Auth.AuthController.login(..))",
                   throwing = "error")
    public void afterFailedAuthentication(JoinPoint joinPoint, Throwable error) {
        logger.warn("Authentication failed: {}", error.getMessage());
    }
} 