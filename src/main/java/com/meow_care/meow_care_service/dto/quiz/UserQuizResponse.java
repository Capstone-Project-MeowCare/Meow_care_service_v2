package com.meow_care.meow_care_service.dto.quiz;

import jakarta.validation.constraints.Size;

import java.util.UUID;

public class UserQuizResponse {

    UUID id;

    @Size(max = 150)
    String answerText;

}
