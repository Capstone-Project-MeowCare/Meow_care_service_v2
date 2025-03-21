package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.quiz.UserQuizResultDto;
import com.meow_care.meow_care_service.dto.quiz.UserQuizResultWithQuizDto;
import com.meow_care.meow_care_service.dto.quiz.UserSummitQuizRequest;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.UserQuizResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-quiz-results")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class UserQuizResultController {

    private final UserQuizResultService userQuizResultService;

    @GetMapping("/{id}")
    public ApiResponse<UserQuizResultDto> getUserQuizResultById(@PathVariable UUID id) {
        return userQuizResultService.get(id);
    }

    //get by user id
    @GetMapping("/user/{userId}")
    public ApiResponse<List<UserQuizResultWithQuizDto>> getUserQuizResultByUserId(@PathVariable UUID userId) {
        return userQuizResultService.getByUserId(userId);
    }

    //get by user id and month and year
    @GetMapping("/user/{userId}/month")
    public ApiResponse<List<UserQuizResultWithQuizDto>> getUserQuizResultByUserIdAndMonth(@PathVariable UUID userId, @RequestParam int month, @RequestParam int year) {
        return userQuizResultService.getByUserIdAndMonth(userId, month, year);
    }

    @GetMapping
    public ApiResponse<List<UserQuizResultDto>> getAllUserQuizResults() {
        return userQuizResultService.getAll();
    }

    @PostMapping
    public ApiResponse<UserQuizResultDto> createUserQuizResult(@RequestBody UserQuizResultDto userQuizResultDto) {
        return userQuizResultService.create(userQuizResultDto);
    }

    //submit quiz
    @PostMapping("/submit")
    public ApiResponse<UserQuizResultDto> submitQuiz(@RequestBody UserSummitQuizRequest userSummitQuizRequest) {
        return userQuizResultService.submitQuiz(userSummitQuizRequest);

    }

    @PutMapping("/{id}")
    public ApiResponse<UserQuizResultDto> updateUserQuizResult(@PathVariable UUID id, @RequestBody UserQuizResultDto userQuizResultDto) {
        return userQuizResultService.update(id, userQuizResultDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUserQuizResult(@PathVariable UUID id) {
        return userQuizResultService.delete(id);
    }
}
