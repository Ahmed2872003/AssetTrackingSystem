package com.AssetTrackingSys.APIGateway.Security.Filter;

import com.AssetTrackingSys.APIGateway.Security.Utils.JWTUtil;
import com.AssetTrackingSys.APIGateway.Security.UserPrincipal;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthFilter implements WebFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.expiration}")
    private Long EXPIRATION_TIME_MILLI;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

//        log.info(exchange.getRequest().getURI().toString());

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }


        String token = authHeader.substring(7);
        JWTUtil jwt = new JWTUtil(JWT_SECRET, EXPIRATION_TIME_MILLI);

        try {
            Claims claims = jwt.getClaimsFromToken(token);


            String username = claims.getSubject();
            Integer userId = claims.get("userId", Integer.class);
            String userRole = claims.get("userRole", String.class);

            UserPrincipal userPrincipal = new UserPrincipal(userId, username);
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userRole.toUpperCase());

            Authentication auth = new UsernamePasswordAuthenticationToken(userPrincipal, null, List.of(authority));



            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));

        } catch (Exception exception) {
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return SecurityWebFiltersOrder.AUTHENTICATION.getOrder();
    }
}
