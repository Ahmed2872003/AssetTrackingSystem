package com.AssetTrackingSys.APIGateway.Security.Filter;

import com.AssetTrackingSys.APIGateway.Security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import java.util.ArrayList;

import reactor.core.publisher.Mono;

@Component
public class UserHeaderIdentifier implements WebFilter, Ordered {


    private static final Logger log = LoggerFactory.getLogger(UserHeaderIdentifier.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        log.info(exchange.getRequest().getURI().toString());

        return ReactiveSecurityContextHolder.getContext()
                .flatMap(context->{
                    Authentication authentication = context.getAuthentication();

                    if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
                        ArrayList<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
                        String userRole = authorities.get(0).getAuthority().split("_", 2)[1];

                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-Id", userPrincipal.getId().toString())
                                .header("X-User-Name", userPrincipal.getUsername())
                                .header("X-User-Role", userRole)
                                .build();

                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(mutatedRequest)
                                .build();

                        return chain.filter(mutatedExchange);
                    }
                    return chain.filter(exchange);

                }).switchIfEmpty(chain.filter(exchange));


    }

    @Override
    public int getOrder() {
        return SecurityWebFiltersOrder.AUTHENTICATION.getOrder() + 1;
    }
}
