package com.example.springcardprojectdemo.service;

import com.example.springcardprojectdemo.entity.User;
import com.example.springcardprojectdemo.entity.Verify;
import com.example.springcardprojectdemo.payload.ApiResponse;
import com.example.springcardprojectdemo.payload.LoginDto;
import com.example.springcardprojectdemo.repository.UserRepository;
import com.example.springcardprojectdemo.repository.VerifyRepository;
import com.example.springcardprojectdemo.security.JwtFilter;
import com.example.springcardprojectdemo.security.JwtProvider;
import com.example.springcardprojectdemo.utills.CommonUtills;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        return optionalUser.orElse(null);
    }

}