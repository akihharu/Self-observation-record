package com.example.selfobservationrecord.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.selfobservationrecord.service.DiaryService;
import com.example.selfobservationrecord.entity.Diary;
import com.example.selfobservationrecord.entity.User;
import com.example.selfobservationrecord.repository.UserRepository;
import java.util.Optional;
import com.example.selfobservationrecord.dto.DiaryCreateRequest;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/diaries")
public class DiaryController {

    private final DiaryService diaryService;
    private final UserRepository userRepository;

    public DiaryController(
            DiaryService diaryService,
            UserRepository userRepository
    ) {
        this.diaryService = diaryService;
        this.userRepository = userRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Diary create(
        @Valid @RequestBody DiaryCreateRequest request,
        HttpSession session
    ){
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null){
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Please log in."
                );
            }
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found"
                );
            }

            User user = userOptional.get();

            if (request.getStatus() == 1
                    && (request.getReflection() == null
                    || request.getReflection().isBlank())) {
            
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please write reflection."
                );
            }

            return
                diaryService.createDiary(
                  user,
                  request.getRecordDate(),
                  request.getEvent(),
                  request.getReflection(),
                  request.getStatus()
                );
    }

    @GetMapping
    public List<Diary> getDiaries(
        HttpSession session
    ){
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null){
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Please log in."
                );
            }
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found"
                );
            }

            User user = userOptional.get();  

            return  diaryService.getDiaries(user);
    }

    @GetMapping("/{id}")
    public Diary getDiary(
        @PathVariable Integer id,
        HttpSession session
        ) {
            Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Please log in."
                );
            }

            Diary diary = diaryService.getDiary(id);
            
            if (diary == null) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Diary not found"
                );
            }

            if (!diary.getUser().getId().equals(userId)) {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot view this diary."
                );
            }

            return diary;
    }

    @PutMapping("/{id}")
    public Diary updateDiary(
        @PathVariable Integer id,
        @Valid @RequestBody DiaryCreateRequest request,
        HttpSession session
    ) {
        Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Please log in."
                );
            }

            Diary diary = diaryService.getDiary(id);

            if (diary == null) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Diary not found"
                );
            }

            if (!diary.getUser().getId().equals(userId)) {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot update this diary."
                );
            }

            if (request.getStatus() == 1
                    && (request.getReflection() == null
                    || request.getReflection().isBlank())) {
        
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please write reflection."
                );
            }

            return diaryService.updateDiary(
                  id,
                  request.getRecordDate(),
                  request.getEvent(),
                  request.getReflection(),
                  request.getStatus()
                );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiary(
        @PathVariable Integer id,
        HttpSession session
    ) {
        Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Please log in."
                );
            }

            Diary diary = diaryService.getDiary(id);
            
            if (diary == null) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Diary not found"
                );
            }

            if (!diary.getUser().getId().equals(userId)) {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot delete this diary."
                );
            }

            diaryService.deleteDiary(id);
      }
}