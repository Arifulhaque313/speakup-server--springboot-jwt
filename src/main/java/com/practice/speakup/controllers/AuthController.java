package com.practice.speakup.controllers;

import com.practice.speakup.dtos.AuthResponse;
import com.practice.speakup.dtos.LoginRequest;
import com.practice.speakup.handlers.GlobalResponseHandler;
import com.practice.speakup.models.User;
import com.practice.speakup.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<GlobalResponseHandler<Map<String, String>>> register(@Valid @RequestBody User user){
        try{
            authService.registerUser(user);
            String roleName = user.getRoleName();

            Map<String, String> responseDetails = new HashMap<>();
            responseDetails.put("name", user.getName());
            responseDetails.put("username", user.getUsername());
            responseDetails.put("email", user.getEmail());
            responseDetails.put("address", user.getAddress());
            responseDetails.put("role", user.getRoleName());

            return GlobalResponseHandler.successResponse("User registered successfully", responseDetails, HttpStatus.CREATED.value());
        }catch (Exception e){
            return GlobalResponseHandler.errorResponse("Registration Failed: "+ e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<GlobalResponseHandler<AuthResponse>> login(@Valid @RequestBody LoginRequest request){
        try{
            AuthResponse authResponse = authService.loginUser(request);

            return GlobalResponseHandler.successResponse("User Login successful", authResponse, HttpStatus.OK.value());
        }catch (Exception e){
            return GlobalResponseHandler.errorResponse("Login Failed: "+ e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
