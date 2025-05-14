package com.AssetTrackingSystem.AssetService.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final long EXECUTION_TIME_THRESHOLD = 1000; // 1 second

    @Around("execution(* com.AssetTrackingSystem.AssetService.Asset.*(..))")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            if (executionTime > EXECUTION_TIME_THRESHOLD) {
                logger.warn("Method {} took {} ms to execute - Performance threshold exceeded", 
                    methodName, executionTime);
            }
            
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("Method {} failed after {} ms: {}", 
                methodName, executionTime, e.getMessage());
            throw e;
        }
    }
} 