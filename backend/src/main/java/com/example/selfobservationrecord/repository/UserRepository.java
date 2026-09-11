package com.example.selfobservationrecord.repository;

import com.example.selfobservationrecord.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}