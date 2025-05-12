package com.AssetTrackingSys.UserService.Exceptions;

public class AccessDeniedException extends RuntimeException {


    public AccessDeniedException(String message) {
        super(message);
    }
}
