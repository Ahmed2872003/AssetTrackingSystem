package com.AssetTrackingSys.UserService.Security.Configuration;

import com.AssetTrackingSys.UserService.Security.Filter.SetUserContextFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.InetAddress;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    private SetUserContextFilter setUserContextFilter;


@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


    http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .anonymous(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authConfig->
                        authConfig
                                .requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(expHandler->
                        expHandler
                                .authenticationEntryPoint((req, res, exp)->{
                                    res.sendError(HttpStatus.UNAUTHORIZED.value());
                                })
                                .accessDeniedHandler((req, res, exp)->{
                                    res.sendError(HttpStatus.FORBIDDEN.value());
                                })
                );

        http.addFilterBefore(setUserContextFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}
