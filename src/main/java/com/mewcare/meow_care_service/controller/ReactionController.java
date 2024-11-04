package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.ReactionDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.ReactionService;
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
@RequestMapping("/reactions")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ReactionController {

    private final ReactionService reactionService;

    @GetMapping("/{id}")
    public ApiResponse<ReactionDto> getReactionById(@PathVariable UUID id) {
        return reactionService.get(id);
    }

    @GetMapping
    public ApiResponse<List<ReactionDto>> getAllReactions() {
        return reactionService.getAll();
    }

    @PostMapping
    public ApiResponse<ReactionDto> createReaction(@RequestBody ReactionDto reactionDto) {
        return reactionService.create(reactionDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<ReactionDto> updateReaction(@PathVariable UUID id, @RequestBody ReactionDto reactionDto) {
        return reactionService.update(id, reactionDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteReaction(@PathVariable UUID id) {
        return reactionService.delete(id);
    }
}
