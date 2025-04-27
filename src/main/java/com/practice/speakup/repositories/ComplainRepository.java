package com.practice.speakup.repositories;

import com.practice.speakup.models.Complain;
import com.practice.speakup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComplainRepository extends JpaRepository<Complain, Long> {
    List<Complain> findByUser(User user);
    Optional<Complain> findByIdAndUser(Long id, User user);
}
