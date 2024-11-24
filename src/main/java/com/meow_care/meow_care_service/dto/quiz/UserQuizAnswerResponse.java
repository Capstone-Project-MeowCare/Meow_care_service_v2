package com.meow_care.meow_care_service.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class UserQuizAnswerResponse {
    UUID id;
    String answerText;
}
