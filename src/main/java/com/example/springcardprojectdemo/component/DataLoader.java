package com.example.springcardprojectdemo.component;

import com.example.springcardprojectdemo.entity.Role;
import com.example.springcardprojectdemo.entity.User;
import com.example.springcardprojectdemo.repository.RoleRepository;
import com.example.springcardprojectdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) {

        if (initMode.equals("always")) {
            Role admin = roleRepository.save(new Role(1L, "ADMIN"));
            Role user = roleRepository.save(new Role(2L, "USER"));

            User user1 = new User("Shaxzod", "Murtozaqulov", "shaxzodmailsender@gmail.com",
                            passwordEncoder.encode("12345"),
                            new HashSet<>(Arrays.asList(admin, user)), true);

            userRepository.save(user1);
        }


    }
}
