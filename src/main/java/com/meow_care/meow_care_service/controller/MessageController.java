package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.MessageDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.MessageService;
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
@RequestMapping("/messages")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{id}")
    public ApiResponse<MessageDto> getMessageById(@PathVariable UUID id) {
        return messageService.get(id);
    }

    @GetMapping
    public ApiResponse<List<MessageDto>> getAllMessages() {
        return messageService.getAll();
    }

    @PostMapping
    public ApiResponse<MessageDto> createMessage(@RequestBody MessageDto messageDto) {
        return messageService.create(messageDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<MessageDto> updateMessage(@PathVariable UUID id, @RequestBody MessageDto messageDto) {
        return messageService.update(id, messageDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMessage(@PathVariable UUID id) {
        return messageService.delete(id);
    }
}
