package com.practice.speakup.services;

import com.practice.speakup.dtos.AuthResponse;
import com.practice.speakup.dtos.LoginRequest;
import com.practice.speakup.models.Role;
import com.practice.speakup.models.User;
import com.practice.speakup.repositories.RoleRepository;
import com.practice.speakup.repositories.UserRepository;
import com.practice.speakup.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    //Register User
    public void registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepo.findByName("ROLE_USER")
                .orElseGet(() -> roleRepo.save(new Role(null, "ROLE_USER")));

        user.setRoles(Collections.singleton(role));
        userRepo.save(user);
    }

    public AuthResponse loginUser(LoginRequest request){
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user.getName());
    }
}
