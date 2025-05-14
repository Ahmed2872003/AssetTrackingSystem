package com.AssetTrackingSys.UserService.Security.Filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import com.AssetTrackingSys.UserService.User.User;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SetUserContextFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(SetUserContextFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String userId = request.getHeader("X-User-Id");
        String username = request.getHeader("X-User-Name");
        String userRole = request.getHeader("X-User-Role");

        if(userId == null || username == null || userRole == null) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = new User(Long.valueOf(userId), username, User.Roles.getIdFromRole(userRole));


        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, List.of(authority));


        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);

    }
}

