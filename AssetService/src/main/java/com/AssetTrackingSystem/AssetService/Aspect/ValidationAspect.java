package com.AssetTrackingSystem.AssetService.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.AssetTrackingSystem.AssetService.Asset.AssetController.*(..))")
    public void validateInput(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        
        for (Object arg : args) {
            if (arg == null) {
                logger.error("Null argument passed to method {}", methodName);
                throw new IllegalArgumentException("Null arguments are not allowed");
            }
            
            // Add specific validation logic for different types
            if (arg instanceof String && ((String) arg).trim().isEmpty()) {
                logger.error("Empty string passed to method {}", methodName);
                throw new IllegalArgumentException("Empty strings are not allowed");
            }
            
            if (arg instanceof Long && (Long) arg <= 0) {
                logger.error("Invalid ID value passed to method {}", methodName);
                throw new IllegalArgumentException("ID must be positive");
            }
        }
    }
} 