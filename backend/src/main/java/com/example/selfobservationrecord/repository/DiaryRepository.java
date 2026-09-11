package com.example.selfobservationrecord.repository;

import com.example.selfobservationrecord.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {
}