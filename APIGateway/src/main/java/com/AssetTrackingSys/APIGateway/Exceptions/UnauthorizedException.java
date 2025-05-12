package com.AssetTrackingSys.APIGateway.Exceptions;

public class UnauthorizedException extends RuntimeException {


    public UnauthorizedException(String message) {
        super(message);
    }
}
