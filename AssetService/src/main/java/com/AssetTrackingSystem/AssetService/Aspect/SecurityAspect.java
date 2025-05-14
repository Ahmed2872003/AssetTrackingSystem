package com.AssetTrackingSystem.AssetService.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public Object auditSecureMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String methodName = joinPoint.getSignature().getName();

        logger.info("User '{}' attempting to access secured method '{}'", 
            auth.getName(), methodName);
        
        try {
            Object result = joinPoint.proceed();
            logger.info("User '{}' successfully executed '{}'", 
                auth.getName(), methodName);
            return result;
        } catch (Exception e) {
            logger.warn("User '{}' failed to execute '{}': {}", 
                auth.getName(), methodName, e.getMessage());
            throw e;
        }
    }
} 