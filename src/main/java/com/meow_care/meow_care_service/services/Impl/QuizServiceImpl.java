package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.quiz.QuizDto;
import com.meow_care.meow_care_service.dto.quiz.QuizQuestionWithAnswerDto;
import com.meow_care.meow_care_service.dto.quiz.QuizWithQuestionsDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Quiz;
import com.meow_care.meow_care_service.entities.QuizQuestion;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.QuizMapper;
import com.meow_care.meow_care_service.mapper.QuizQuestionMapper;
import com.meow_care.meow_care_service.repositories.QuizRepository;
import com.meow_care.meow_care_service.services.QuizService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuizServiceImpl extends BaseServiceImpl<QuizDto, Quiz, QuizRepository, QuizMapper> implements QuizService {

    private final QuizQuestionMapper quizQuestionMapper;

    public QuizServiceImpl(QuizRepository repository, QuizMapper mapper, QuizQuestionMapper quizQuestionMapper) {
        super(repository, mapper);
        this.quizQuestionMapper = quizQuestionMapper;
    }

    @Override
    public ApiResponse<QuizWithQuestionsDto> getWithQuestions(UUID id) {
        Quiz quiz = repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(mapper.toDtoWithQuestions(quiz));
    }

    @Override
    public ApiResponse<QuizWithQuestionsDto> createWithQuestions(QuizWithQuestionsDto dto) {
        Quiz quiz = mapper.toEntityWithQuestions(dto);
        quiz = repository.save(quiz);
        return ApiResponse.success(mapper.toDtoWithQuestions(quiz));
    }

    @Override
    public ApiResponse<List<QuizWithQuestionsDto>> getAllWithQuestions() {
        List<Quiz> quizzes = repository.findAll();
        return ApiResponse.success(mapper.toDtoWithQuestions(quizzes));
    }

    @Override
    public ApiResponse<QuizWithQuestionsDto> addQuestion(UUID id, List<QuizQuestionWithAnswerDto> quizWithQuestionsDto) {
        Quiz quiz = repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        List<QuizQuestion> questions = quizQuestionMapper.toEntityWithAnswers(quizWithQuestionsDto);
        questions.forEach(quizQuestion -> quizQuestion.setQuiz(quiz));
        quiz.getQuizQuestions().addAll(questions);
        Quiz savedQuiz = repository.save(quiz);
        return ApiResponse.success(mapper.toDtoWithQuestions(savedQuiz));
    }
}
