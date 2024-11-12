package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.QuizQuestionDto;
import com.meow_care.meow_care_service.dto.QuizQuestionWithAnswerDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.QuizQuestionService;
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
@RequestMapping("/quiz-questions")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class QuizQuestionController {

    private final QuizQuestionService quizQuestionService;

    @GetMapping("/{id}")
    public ApiResponse<QuizQuestionDto> getQuizQuestionById(@PathVariable UUID id) {
        return quizQuestionService.get(id);
    }

    @GetMapping
    public ApiResponse<List<QuizQuestionDto>> getAllQuizQuestions() {
        return quizQuestionService.getAll();
    }

    @PostMapping
    public ApiResponse<QuizQuestionDto> createQuizQuestion(@RequestBody QuizQuestionDto quizQuestionDto) {
        return quizQuestionService.create(quizQuestionDto);
    }

    //create with quiz  answer
    @PostMapping("/quiz-answer")
    public ApiResponse<QuizQuestionWithAnswerDto> createQuizQuestionWithAnswer(@RequestParam UUID quizId, @RequestBody QuizQuestionWithAnswerDto quizQuestionDto) {
        return quizQuestionService.createWithAnswer(quizId,quizQuestionDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<QuizQuestionDto> updateQuizQuestion(@PathVariable UUID id, @RequestBody QuizQuestionDto quizQuestionDto) {
        return quizQuestionService.update(id, quizQuestionDto);
    }

    //update with quiz answer
    @PutMapping("/quiz-answer/{id}")
    public ApiResponse<QuizQuestionWithAnswerDto> updateQuizQuestionWithAnswer(@PathVariable UUID id, @RequestBody QuizQuestionWithAnswerDto quizQuestionDto) {
        return quizQuestionService.updateWithAnswer(id, quizQuestionDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteQuizQuestion(@PathVariable UUID id) {
        return quizQuestionService.delete(id);
    }
}
