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
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.AssetTrackingSys.UserService.User.*.*(..)) || " + "execution(* com.AssetTrackingSys.UserService.Auth.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Starting method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* com.AssetTrackingSys.UserService.User.*.*(..)) || " + "execution(* com.AssetTrackingSys.UserService.Auth.*.*(..))")
    public void logAfterReturning(JoinPoint joinPoint) {
        logger.info("Successfully completed: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "execution(* com.AssetTrackingSys.UserService.User.*.*(..)) || " + "execution(* com.AssetTrackingSys.UserService.Auth.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Exception in {}: {}", joinPoint.getSignature().getName(), error.getMessage());
    }
} 