package com.practice.speakup.controllers;

import com.practice.speakup.handlers.GlobalResponseHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")

public class CommonController {
    @GetMapping("/public")
    public ResponseEntity<GlobalResponseHandler<String>> publicHello(){
        try{
            String responseDetails = "Details for public endpoint";

            return GlobalResponseHandler.successResponse("Public API Request Successful", responseDetails, HttpStatus.OK.value());
        }catch (Exception e){
            return GlobalResponseHandler.errorResponse("An error occurred "+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/public/health-check")
    public ResponseEntity<GlobalResponseHandler<String>> healthCheck(){
        try{
            String responseDetails = "Everything is ok";

            return GlobalResponseHandler.successResponse("Health Check Request Successful", responseDetails, HttpStatus.OK.value());
        }catch (Exception e){
            return GlobalResponseHandler.errorResponse("An error occurred "+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<GlobalResponseHandler<String>> userHello(){
        try{
            String responseDetails = "Hello User - you are authenticated";

            return GlobalResponseHandler.successResponse("User Request Successful", responseDetails, HttpStatus.OK.value());
        }catch (Exception e){
            return GlobalResponseHandler.errorResponse("An error occurred "+ e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<GlobalResponseHandler<String>> adminHello(){
        try{
            String responseDetails = "Hello Admin - you are authenticated";

            return GlobalResponseHandler.successResponse("Admin Request Successful", responseDetails, HttpStatus.OK.value());
        }catch (Exception e){
            return GlobalResponseHandler.errorResponse("An error occurred "+ e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
