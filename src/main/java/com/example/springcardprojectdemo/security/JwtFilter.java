package com.example.springcardprojectdemo.security;

import com.example.springcardprojectdemo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
            boolean validateToken = jwtProvider.validateToken(token);

            if (validateToken) {
                String username = jwtProvider.getUsernameFromToken(token);
                System.out.println(username);

                UserDetails userDetails = authService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

//                System.out.println(SecurityContextHolder.getContext().getAuthentication());
                SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
//                System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }
        }
        filterChain.doFilter(request, response);
    }
}
