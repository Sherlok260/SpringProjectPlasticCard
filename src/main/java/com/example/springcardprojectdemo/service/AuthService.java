package com.example.springcardprojectdemo.service;

import com.example.springcardprojectdemo.entity.User;
import com.example.springcardprojectdemo.payload.ApiResponse;
import com.example.springcardprojectdemo.payload.LoginDto;
import com.example.springcardprojectdemo.repository.UserRepository;
import com.example.springcardprojectdemo.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        return optionalUser.orElse(null);
    }

    public ApiResponse login(LoginDto dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getPassword()));
        String token = jwtProvider.generateToken(dto.getEmail());
        System.out.println(token);
        return new ApiResponse("foydalanuvchi", true, token);
    }
}
