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
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    MailService mailService;
    @Autowired
    VerifyRepository verifyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        return optionalUser.orElse(null);
    }

    public ApiResponse login(LoginDto dto) {
        System.out.println(dto);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getPassword()));
        String token = jwtProvider.generateToken(dto.getEmail());
        System.out.println(token);
        return new ApiResponse("foydalanuvchi", true, token);
    }

    public ApiResponse f_password(String email) {
        try {
            int new_verify_code = CommonUtills.generateCode();
            verifyRepository.save(new Verify((long) new_verify_code, email));
            mailService.sendTextt(email, String.valueOf(new_verify_code));
            return new ApiResponse("recovery code is sending", true, jwtProvider.generateToken(email));
        } catch (Exception e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }

    public ApiResponse v_f_password(long v_code) {
        String email = JwtFilter.getEmailWithToken;
        if (verifyRepository.findByEmail(email).get().getVerifyCode().equals(v_code)) {
            verifyRepository.deleteByEmail(email);
            return new ApiResponse("Recovery code is true. Please enter new code for your account", true);
        } else return new ApiResponse("recovery code is incorrect", false);
    }

    public ApiResponse set_new_password(String new_password) {
        try {
            String email = JwtFilter.getEmailWithToken;
            userRepository.updatePassword(email, passwordEncoder.encode(new_password));
            return new ApiResponse("password replace is successfully", true);
        } catch (Exception e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }
}