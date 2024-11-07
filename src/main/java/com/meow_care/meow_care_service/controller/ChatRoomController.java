package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.ChatRoomDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.ChatRoomService;
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
@RequestMapping("/chat-rooms")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/{id}")
    public ApiResponse<ChatRoomDto> getChatRoomById(@PathVariable UUID id) {
        return chatRoomService.get(id);
    }

    @GetMapping
    public ApiResponse<List<ChatRoomDto>> getAllChatRooms() {
        return chatRoomService.getAll();
    }

    @PostMapping
    public ApiResponse<ChatRoomDto> createChatRoom(@RequestBody ChatRoomDto chatRoomDto) {
        return chatRoomService.create(chatRoomDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<ChatRoomDto> updateChatRoom(@PathVariable UUID id, @RequestBody ChatRoomDto chatRoomDto) {
        return chatRoomService.update(id, chatRoomDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteChatRoom(@PathVariable UUID id) {
        return chatRoomService.delete(id);
    }
}
