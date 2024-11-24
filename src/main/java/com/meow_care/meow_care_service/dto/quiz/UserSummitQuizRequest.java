package com.meow_care.meow_care_service.dto.quiz;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class UserSummitQuizRequest {

    @NotNull
    UUID id;

    @NotNull
    private List<SubmittedQuestion> questions;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class SubmittedQuestion {

        @NotNull
        UUID id;

        @NotNull
        List<UUID> answersId;

    }
}
