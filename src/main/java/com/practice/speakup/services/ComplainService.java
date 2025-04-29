package com.practice.speakup.services;

import com.practice.speakup.dtos.ComplainRequest;
import com.practice.speakup.dtos.ComplainResponse;
import com.practice.speakup.models.Complain;
import com.practice.speakup.models.User;
import com.practice.speakup.repositories.ComplainRepository;
import com.practice.speakup.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComplainService {

    private final ComplainRepository complainRepository;
    private final UserRepository userRepository;

    public User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ComplainResponse createComplain(ComplainRequest request){
        User user = getCurrentUser();
        Complain complain = Complain.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .build();

        Complain saved = complainRepository.save(complain);
        return  mapToResponse(saved);
    }

    public List<ComplainResponse> getUserComplains(){
        User user = getCurrentUser();
        return complainRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ComplainResponse getUserComplainById(Long id){
        User user = getCurrentUser();
        Complain complain = complainRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Complain no found or does not belongs to the user"));
                return mapToResponse(complain);
    }

    public  ComplainResponse updateComplain(Long id, ComplainRequest request){
        User currentUser = getCurrentUser();
        Complain complain = complainRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Complain not found or dose not belongs to the user"));
        return mapToResponse(complainRepository.save(complain));
    }

    public  void deleteComplain(Long id){
        User currentUser = getCurrentUser();
        Complain complain = complainRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Complain not found or dose not belongs to the user"));

        complainRepository.delete(complain);
    }

    private ComplainResponse mapToResponse(Complain complain){
        return new ComplainResponse(
                complain.getId(),
                complain.getTitle(),
                complain.getDescription(),
                complain.getUser().getName(),
                complain.getUser().getUsername()
        );
    }
}
