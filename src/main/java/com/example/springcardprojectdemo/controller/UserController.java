package com.example.springcardprojectdemo.controller;

import com.example.springcardprojectdemo.entity.Role;
import com.example.springcardprojectdemo.entity.User;
import com.example.springcardprojectdemo.entity.Verify;
import com.example.springcardprojectdemo.payload.ApiResponse;
import com.example.springcardprojectdemo.payload.RegisterDto;
import com.example.springcardprojectdemo.repository.UserRepository;
import com.example.springcardprojectdemo.repository.VerifyRepository;
import com.example.springcardprojectdemo.security.JwtFilter;
import com.example.springcardprojectdemo.security.JwtProvider;
import com.example.springcardprojectdemo.service.MailService;
import com.example.springcardprojectdemo.utills.CommonUtills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/register")
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerifyRepository verifyRepository;

    @Autowired
    MailService mailService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping
    public HttpEntity<?> register(@RequestBody RegisterDto dto) {
        String token = jwtProvider.generateToken(dto.getEmail());
        System.out.println(token);
        Optional<User> byEmail = userRepository.findByEmail(dto.getEmail());
        Long code = Long.valueOf(CommonUtills.generateCode());
        User new_user = new User(dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                new Role(3L, "GUEST"),
                true, false);
        if(byEmail.isPresent()) {
            if (byEmail.get().isVerified()) {
                return ResponseEntity.ok("Bunday foydalanuvchi mavjud");
            } else {
                userRepository.deleteByEmail(dto.getEmail());
                userRepository.save(new_user);
                verifyRepository.deleteByEmail(dto.getEmail());
                verifyRepository.save(new Verify(code, dto.getEmail()));
                mailService.sendTextt(dto.getEmail(), code.toString());
               return ResponseEntity.ok(new ApiResponse("Code is sended", true, token));
           }
        } else {
            userRepository.save(new_user);
            verifyRepository.save(new Verify(code, dto.getEmail()));
            mailService.sendTextt(dto.getEmail(), code.toString());
            return ResponseEntity.ok(new ApiResponse("Code is sended fow new user", true, token));
        }
    }


    @PreAuthorize(value = "hasRole('GUEST')")
    @PostMapping("/verify")
    public HttpEntity<?> verify(@RequestParam Long code) {
        String getEmailWithToken = JwtFilter.getEmailWithToken;
        if (verifyRepository.findByEmail(getEmailWithToken).getVerifyCode().equals(code)) {
            userRepository.updateVerifyAndRoleWithEmail(getEmailWithToken, new Role(2L, "USER"));
            return ResponseEntity.ok(new ApiResponse("Verification is success", true));
        } else return ResponseEntity.ok(new ApiResponse("verify code is incorrect", false));
    }

}
