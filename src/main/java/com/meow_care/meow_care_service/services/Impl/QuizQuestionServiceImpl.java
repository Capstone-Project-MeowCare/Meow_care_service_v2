package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.QuizAnswerDto;
import com.meow_care.meow_care_service.dto.QuizQuestionDto;
import com.meow_care.meow_care_service.dto.QuizQuestionWithAnswerDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.QuizAnswer;
import com.meow_care.meow_care_service.entities.QuizQuestion;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.QuizAnswerMapper;
import com.meow_care.meow_care_service.mapper.QuizQuestionMapper;
import com.meow_care.meow_care_service.repositories.QuizAnswerRepository;
import com.meow_care.meow_care_service.repositories.QuizQuestionRepository;
import com.meow_care.meow_care_service.services.QuizQuestionService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuizQuestionServiceImpl extends BaseServiceImpl<QuizQuestionDto, QuizQuestion, QuizQuestionRepository, QuizQuestionMapper> implements QuizQuestionService {

    private final QuizAnswerMapper answerMapper;

    private final QuizAnswerRepository quizAnswerRepository;

    public QuizQuestionServiceImpl(QuizQuestionRepository repository, QuizQuestionMapper mapper, QuizAnswerMapper answerMapper, QuizAnswerRepository quizAnswerRepository) {
        super(repository, mapper);
        this.answerMapper = answerMapper;
        this.quizAnswerRepository = quizAnswerRepository;
    }


    @Override
    public ApiResponse<QuizQuestionWithAnswerDto> createWithAnswer(UUID quizId, QuizQuestionWithAnswerDto quizQuestionDto) {
        QuizQuestion quizQuestion = mapper.toEntityWithAnswers(quizQuestionDto);
        quizQuestion.setQuizId(quizId);
        QuizQuestion savedQuizQuestion = repository.save(quizQuestion);
        return ApiResponse.success(mapper.toDtoWithAnswers(savedQuizQuestion));
    }

    @Override
    public ApiResponse<QuizQuestionWithAnswerDto> addAnswerToQuizQuestion(UUID id, List<QuizAnswerDto> answers) {
        QuizQuestion quizQuestion = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Quiz question not found with id: " + id)
        );
        List<QuizAnswer> answerEntities = answerMapper.toEntity(answers);

        //set quiz question to answer
        answerEntities.forEach(answer -> answer.setQuestion(quizQuestion));

        quizQuestion.getQuizAnswers().addAll(answerEntities);
        QuizQuestion savedQuizQuestion = repository.save(quizQuestion);
        return ApiResponse.success(mapper.toDtoWithAnswers(savedQuizQuestion));
    }

    @Override
    public ApiResponse<QuizQuestionWithAnswerDto> removeAnswerToQuizQuestion(UUID id, List<QuizAnswerDto> answers) {
        QuizQuestion quizQuestion = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Quiz question not found with id: " + id)
        );
        List<QuizAnswer> answerEntities = answerMapper.toEntity(answers);
        answerEntities.forEach(quizQuestion.getQuizAnswers()::remove);
        QuizQuestion savedQuizQuestion = repository.save(quizQuestion);

        quizAnswerRepository.deleteAll(answerEntities);

        return ApiResponse.success(mapper.toDtoWithAnswers(savedQuizQuestion));
    }
}
