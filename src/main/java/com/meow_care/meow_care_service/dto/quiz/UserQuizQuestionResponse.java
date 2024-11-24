package com.meow_care.meow_care_service.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class UserQuizQuestionResponse {

    UUID id;

    String questionText;

    String questionType;

    List<UserQuizAnswerResponse> quizAnswers;

}
