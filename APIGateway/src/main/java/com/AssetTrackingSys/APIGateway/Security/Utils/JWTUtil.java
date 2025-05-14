package com.AssetTrackingSys.APIGateway.Security.Utils;

import com.AssetTrackingSys.APIGateway.Security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import java.util.Date;


public class JWTUtil {

    private final String SECRET_KEY;

    private final Long EXPIRATION_TIME_MILLI;

    public JWTUtil(String SECRET_KEY, Long EXPIRATION_TIME_MILLI){
        this.SECRET_KEY = SECRET_KEY;
        this.EXPIRATION_TIME_MILLI = EXPIRATION_TIME_MILLI;
    }

//    public static void main(String[] Args){
//        UserPrincipal userPrincipal = new UserPrincipal(1, "Ahmed");
//
//        JWTUtil jwt = new JWTUtil("N5WhWwSLgEQJwQl/GUJEWXwwtY4yPrMhEg9M00jRGvgYyC5X137sXxbVp3bZgJd9EkLQ18rkIwkVZU7XhPJ7SA==", 432000000l);
//
//        String token = jwt.generateToken(userPrincipal, "Admin");
//
//        System.out.println(token);
//    }


    public  String generateToken(UserPrincipal userClaims, String role) {
        return Jwts.builder()
                .setSubject(userClaims.getUsername())
                .claim("userId", userClaims.getId())
                .claim("userRole", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLI))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }


    public  Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public  String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

}
