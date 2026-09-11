package com.example.selfobservationrecord.repository;

import com.example.selfobservationrecord.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserId(String userId);
}