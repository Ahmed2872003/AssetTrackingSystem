package com.AssetTrackingSys.APIGateway.Exceptions;

public class AccessDeniedException extends RuntimeException {


    public AccessDeniedException(String message) {
        super(message);
    }
}
