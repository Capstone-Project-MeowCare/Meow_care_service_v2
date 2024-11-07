package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.CommentDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.CommentService;
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
@RequestMapping("/comments")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public ApiResponse<CommentDto> getCommentById(@PathVariable UUID id) {
        return commentService.get(id);
    }

    @GetMapping
    public ApiResponse<List<CommentDto>> getAllComments() {
        return commentService.getAll();
    }

    @PostMapping
    public ApiResponse<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        return commentService.create(commentDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<CommentDto> updateComment(@PathVariable UUID id, @RequestBody CommentDto commentDto) {
        return commentService.update(id, commentDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteComment(@PathVariable UUID id) {
        return commentService.delete(id);
    }
}
