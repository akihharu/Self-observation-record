package com.example.selfobservationrecord.repository;

import com.example.selfobservationrecord.entity.Diary;
import com.example.selfobservationrecord.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    List<Diary> findByUserOrderByRecordDateDesc(User user);
    
    Optional<Diary> findByUserAndRecordDate(User user, LocalDate recordDate);
}