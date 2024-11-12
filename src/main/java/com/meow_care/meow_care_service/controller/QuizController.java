package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.QuizDto;
import com.meow_care.meow_care_service.dto.QuizQuestionWithAnswerDto;
import com.meow_care.meow_care_service.dto.QuizWithQuestionsDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/{id}")
    public ApiResponse<QuizWithQuestionsDto> getQuizById(@PathVariable UUID id) {
        return quizService.getWithQuestions(id);
    }

    @GetMapping
    public ApiResponse<List<QuizDto>> getAllQuizzes() {
        return quizService.getAll();
    }

    //get with questions
    @GetMapping("/with-questions")
    public ApiResponse<List<QuizWithQuestionsDto>> getAllQuizzesWithQuestions() {
        return quizService.getAllWithQuestions();
    }

    @PostMapping
    public ApiResponse<QuizDto> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.create(quizDto);
    }

    @PostMapping("/with-questions")
    public ApiResponse<QuizWithQuestionsDto> createQuizWithQuestions(@RequestBody QuizWithQuestionsDto quizWithQuestionsDto) {
        return quizService.createWithQuestions(quizWithQuestionsDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<QuizDto> updateQuiz(@PathVariable UUID id, @RequestBody QuizDto quizDto) {
        return quizService.update(id, quizDto);
    }

    @PutMapping("/add-question/{id}")
    public ApiResponse<QuizWithQuestionsDto> addQuestionToQuiz(@PathVariable UUID id, @RequestBody List<QuizQuestionWithAnswerDto> quizWithQuestionsDto) {
        return quizService.addQuestion(id, quizWithQuestionsDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteQuiz(@PathVariable UUID id) {
        return quizService.delete(id);
    }

}
