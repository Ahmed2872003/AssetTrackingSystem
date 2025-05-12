package com.AssetTrackingSys.APIGateway.ExceptionHandler;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.AssetTrackingSys.APIGateway.Exceptions.UnauthorizedException;
import com.AssetTrackingSys.APIGateway.Exceptions.AccessDeniedException;
import com.AssetTrackingSys.APIGateway.Exceptions.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessDeniedException ex){
        return formatExceptionMsg("Forbidden", ex);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthenticationException(UnauthorizedException ex){
        return formatExceptionMsg("Unauthorized", ex);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException ex) {
        return formatExceptionMsg("Not found", ex);
    }


    private String formatExceptionMsg(String ExceptionType, Exception ex){
        return ExceptionType + ((ex.getMessage().isEmpty()) ? ""  : ": " + ex.getMessage());
    }

}
