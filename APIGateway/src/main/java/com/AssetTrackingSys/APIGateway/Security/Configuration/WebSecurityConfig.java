package com.AssetTrackingSys.APIGateway.Security.Configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;


import com.AssetTrackingSys.APIGateway.Security.Filter.JwtAuthFilter;
import com.AssetTrackingSys.APIGateway.Security.Filter.UserHeaderIdentifier;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private UserHeaderIdentifier userHeaderIdentifier;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authConfig->
                        authConfig
                        .pathMatchers("/token", "/user-service/auth/**").permitAll()
                        .anyExchange().authenticated())

                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);


        http.addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION);


        http.addFilterAfter(userHeaderIdentifier, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }


}