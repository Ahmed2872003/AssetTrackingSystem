package com.AssetTrackingSys.APIGateway.Exceptions;


public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}