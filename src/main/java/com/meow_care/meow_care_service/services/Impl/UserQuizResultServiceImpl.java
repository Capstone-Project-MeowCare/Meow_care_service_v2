package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.quiz.UserQuizResultDto;
import com.meow_care.meow_care_service.dto.quiz.UserQuizResultWithQuizDto;
import com.meow_care.meow_care_service.dto.quiz.UserSummitQuizRequest;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Quiz;
import com.meow_care.meow_care_service.entities.QuizAnswer;
import com.meow_care.meow_care_service.entities.QuizQuestion;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.entities.UserQuizResult;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.UserQuizResultMapper;
import com.meow_care.meow_care_service.repositories.UserQuizResultRepository;
import com.meow_care.meow_care_service.services.QuizService;
import com.meow_care.meow_care_service.services.UserQuizResultService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserQuizResultServiceImpl extends BaseServiceImpl<UserQuizResultDto, UserQuizResult, UserQuizResultRepository, UserQuizResultMapper> implements UserQuizResultService {

    private final QuizService quizService;

    public UserQuizResultServiceImpl(UserQuizResultRepository repository, UserQuizResultMapper mapper, QuizService quizService) {
        super(repository, mapper);
        this.quizService = quizService;
    }

    @Override
    public ApiResponse<UserQuizResultDto> create(UserQuizResultDto userQuizResultDto) {
        UserQuizResult userQuizResult = mapper.toEntity(userQuizResultDto);
        userQuizResult.setUserId(UserUtils.getCurrentUserId());
        userQuizResult = repository.save(userQuizResult);
        return ApiResponse.success(mapper.toDto(userQuizResult));
    }

    @Override
    public ApiResponse<List<UserQuizResultWithQuizDto>> getByUserId(UUID userId) {
        List<UserQuizResult> userQuizResults = repository.findByUserId(userId);
        return ApiResponse.success(mapper.toDtoWithQuizList(userQuizResults));
    }

    //get by user id and month
    @Override
    public ApiResponse<List<UserQuizResultWithQuizDto>> getByUserIdAndMonth(UUID userId, int month, int year) {
        Instant firstDayOfMonth = LocalDate.of(year, month, 1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant lastDayOfMonth = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();
        List<UserQuizResult> userQuizResults = repository.findByUserIdAndCreatedAtGreaterThanAndCreatedAtLessThan(userId, firstDayOfMonth, lastDayOfMonth);
        return ApiResponse.success(mapper.toDtoWithQuizList(userQuizResults));
    }

    @Override
    public ApiResponse<UserQuizResultDto> submitQuiz(UserSummitQuizRequest userSummitQuizRequest) {
        // Find the quiz by ID
        Quiz quiz = quizService.findEntityById(userSummitQuizRequest.getId());

        // If quiz is not found, throw an exception
        if (quiz == null) {
            throw new ApiException(ApiStatus.NOT_FOUND);
        }

        Set<QuizQuestion> quizQuestions = quiz.getQuizQuestions();

        // Validate the number of questions
        if (quizQuestions.size() != userSummitQuizRequest.getQuestions().size()) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid quiz questions");
        }

        AtomicInteger correctAnswers = new AtomicInteger();
        userSummitQuizRequest.getQuestions().forEach(question -> {
            // Find the quiz question by ID
            QuizQuestion quizQuestion = quizQuestions.stream().filter(q -> q.getId().equals(question.getId())).findFirst().orElse(null);

            // If quiz question is not found, throw an exception
            if (quizQuestion == null) {
                throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid quiz questions");
            }

            Set<QuizAnswer> correctAnswersSet = quizQuestion.getCorrectAnswer();

            // Validate the number of answers
            if (correctAnswersSet.size() != question.getAnswersId().size()) {
                return;
            }

            // Check if all answers are correct
            if (correctAnswersSet.stream().allMatch(answer -> question.getAnswersId().contains(answer.getId()))) {
                correctAnswers.getAndIncrement();
            }
        });

        // Calculate the score
        BigDecimal score = BigDecimal.valueOf(correctAnswers.get()).divide(BigDecimal.valueOf(quizQuestions.size()), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        // Create and save the user quiz result
        UserQuizResult userQuizResult = UserQuizResult.builder()
                .quiz(quiz)
                .user(User.builder().id(UserUtils.getCurrentUserId()).build())
                .score(score)
                .build();

        userQuizResult = repository.save(userQuizResult);

        // Return the result
        return ApiResponse.success(mapper.toDto(userQuizResult));
    }

}
