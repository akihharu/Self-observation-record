package com.example.selfobservationrecord.service;

import com.example.selfobservationrecord.entity.Diary;
import com.example.selfobservationrecord.entity.User;
import com.example.selfobservationrecord.repository.DiaryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public Diary createDiary(
            User user,
            LocalDate recordDate,
            String event,
            String reflection,
            Integer status
    ) {

        if (status == 1 && (reflection == null || reflection.isBlank())) {
            throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Reflection is required for completed diary.");
        }

        if (diaryRepository.findByUserAndRecordDate(user, recordDate).isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "A diary already exists for this date."
            );
        }
        Diary diary = new Diary();

        LocalDateTime now = LocalDateTime.now();

        diary.setUser(user);
        diary.setRecordDate(recordDate);
        diary.setEvent(event);
        diary.setReflection(reflection);
        diary.setStatus(status);
        diary.setCreatedAt(now);
        diary.setUpdatedAt(now);

        return diaryRepository.save(diary);
    }

    public List<Diary> getDiaries(User user) {
        return diaryRepository.findByUserOrderByRecordDateDesc(user);
    }

    public Diary getDiary(Integer id) {
        return diaryRepository.findById(id).orElse(null);
    }

    public Diary updateDiary(
            Integer id,
            LocalDate recordDate,
            String event,
            String reflection,
            Integer status
    ) {
        Diary diary = diaryRepository.findById(id).orElse(null);

        if (diary == null) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();

        diary.setRecordDate(recordDate);
        diary.setEvent(event);
        diary.setReflection(reflection);
        diary.setStatus(status);
        diary.setCreatedAt(now);
        diary.setUpdatedAt(now);

        return diaryRepository.save(diary);
    }


    public boolean deleteDiary(Integer id) {
        if (!diaryRepository.existsById(id)) {
            return false;
        }
    
        diaryRepository.deleteById(id);
        return true;
    }
}