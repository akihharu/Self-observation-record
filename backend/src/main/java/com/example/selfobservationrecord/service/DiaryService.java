package com.example.selfobservationrecord.service;

import com.example.selfobservationrecord.entity.Diary;
import com.example.selfobservationrecord.entity.User;
import com.example.selfobservationrecord.repository.DiaryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        Diary diary = new Diary();

        diary.setUser(user);
        diary.setRecordDate(recordDate);
        diary.setEvent(event);
        diary.setReflection(reflection);
        diary.setStatus(status);

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

        diary.setRecordDate(recordDate);
        diary.setEvent(event);
        diary.setReflection(reflection);
        diary.setStatus(status);

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