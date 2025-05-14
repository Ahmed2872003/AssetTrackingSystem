package com.AssetTrackingSystem.AssetService.Exceptions;

public class AccessDeniedException extends RuntimeException {


    public AccessDeniedException(String message) {
        super(message);
    }
}
