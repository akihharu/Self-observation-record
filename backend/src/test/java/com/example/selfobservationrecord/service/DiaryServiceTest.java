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