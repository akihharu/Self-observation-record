package com.example.selfobservationrecord.service;

import com.example.selfobservationrecord.repository.DiaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import com.example.selfobservationrecord.entity.Diary;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.example.selfobservationrecord.entity.User;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

    @Mock
    private DiaryRepository diaryRepository;

    @InjectMocks
    private DiaryService diaryService;

    @Test
    void invalidStatusThrowsException() {

        Diary diary = new Diary();
        
        when(diaryRepository.findById(7))
                .thenReturn(Optional.of(diary));

        assertThrows(
                ResponseStatusException.class,
                () -> diaryService.updateDiary(
                        7,
                        LocalDate.of(2026, 9, 23),
                        "test",
                        "test",
                        2,
                        false
                )
        );
    }

    @Test
    void completedDiaryWithoutReflectionThrowsException() {

        Diary diary = new Diary();

        when(diaryRepository.findById(7))
                .thenReturn(Optional.of(diary));

        assertThrows(
                ResponseStatusException.class,
                () -> diaryService.updateDiary(
                        7,
                        LocalDate.of(2026, 9, 23),
                        "test",
                        "",
                        1,
                        false
                )
        );
    }

    @Test
    void duplicateDateWithoutOverwriteThrowsException() {
    
        Diary diary = new Diary();
        Diary existingDiary = org.mockito.Mockito.mock(Diary.class);
    
        when(existingDiary.getId()).thenReturn(8);
    
        when(diaryRepository.findById(7))
                .thenReturn(Optional.of(diary));
    
        when(diaryRepository.findByUserAndRecordDate(
                diary.getUser(),
                LocalDate.of(2026, 9, 23)
        )).thenReturn(Optional.of(existingDiary));
    
        assertThrows(
                ResponseStatusException.class,
                () -> diaryService.updateDiary(
                        7,
                        LocalDate.of(2026, 9, 23),
                        "test",
                        "test",
                        0,
                        false
                )
        );
    }

    @Test
    void duplicateDateWithOverwriteUpdatesDiary() {

        Diary diary = new Diary();
        Diary existingDiary = org.mockito.Mockito.mock(Diary.class);

        when(existingDiary.getId()).thenReturn(8);

        when(diaryRepository.findById(7))
                .thenReturn(Optional.of(diary));

        when(diaryRepository.findByUserAndRecordDate(
                diary.getUser(),
                LocalDate.of(2026, 9, 23)
        )).thenReturn(Optional.of(existingDiary));

        diaryService.updateDiary(
                7,
                LocalDate.of(2026, 9, 23),
                "updated",
                "updated reflection",
                1,
                true
        );

        verify(diaryRepository).delete(existingDiary);
        verify(diaryRepository).flush();
        verify(diaryRepository).save(diary);
    }

    @Test
    void createDiaryWithInvalidStatusThrowsException() {

        assertThrows(
                ResponseStatusException.class,
                () -> diaryService.createDiary(
                        null,
                        LocalDate.of(2026, 9, 24),
                        "test",
                        "test",
                        2
                )
        );
    }

    @Test
    void createDiaryCompletedWithoutReflectionThrowsException() {

        assertThrows(
                ResponseStatusException.class,
                () -> diaryService.createDiary(
                        null,
                        LocalDate.of(2026, 9, 25),
                        "test",
                        "",
                        1
                )
        );
    }

    @Test
    void createDiaryDuplicateDateThrowsException() {
        User user = new User();
        user.setUserId("testuser");
        user.setPassword("password123");

        LocalDate recordDate = LocalDate.of(2026, 9, 26);

        Diary existingDiary = new Diary();

        when(diaryRepository.findByUserAndRecordDate(user, recordDate))
                .thenReturn(Optional.of(existingDiary));

        assertThrows(
                ResponseStatusException.class,
                () -> diaryService.createDiary(
                        user, recordDate, "test", "", 0
                )
        );
    }

}
