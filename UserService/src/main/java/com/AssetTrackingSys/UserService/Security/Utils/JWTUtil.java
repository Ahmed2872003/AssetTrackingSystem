package com.AssetTrackingSys.UserService.Security.Utils;

import com.AssetTrackingSys.UserService.User.User;
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

    public  String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getName())
                .claim("userId", user.getId())
                .claim("userRole", User.Roles.getRoleFromId(user.getRole_id()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLI))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
