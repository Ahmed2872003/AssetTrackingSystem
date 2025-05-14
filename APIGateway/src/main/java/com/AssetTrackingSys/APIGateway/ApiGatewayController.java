package com.AssetTrackingSys.APIGateway;


import com.AssetTrackingSys.APIGateway.Exceptions.NotFoundException;
import com.AssetTrackingSys.APIGateway.Security.UserPrincipal;
import com.AssetTrackingSys.APIGateway.Security.Utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ApiGatewayController {

    private static final Logger log = LoggerFactory.getLogger(ApiGatewayController.class);
    @Value("${jwt.secret}")
    private String JWT_SECRET_KEY;

    @Value("${jwt.expiration}")
    private Long JWT_EXPIRATION_TIME;


    // THIS METHOD FOR TESTING
    @GetMapping("/token")
    public String getToken(){
        log.info(JWT_SECRET_KEY, JWT_EXPIRATION_TIME);

        UserPrincipal userPrincipal = new UserPrincipal(1, "Ahmed");


        JWTUtil jwt = new JWTUtil(JWT_SECRET_KEY, JWT_EXPIRATION_TIME);

        return "Bearer " + jwt.generateToken(userPrincipal, "ADMIN");
    }

}
