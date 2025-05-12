package com.AssetTrackingSys.UserService.Common.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotFoundController {

    @RequestMapping("/**")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound(){}


}
