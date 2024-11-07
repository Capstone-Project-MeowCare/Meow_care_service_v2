package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ChatRoomDto;
import com.meow_care.meow_care_service.entities.ChatRoom;
import com.meow_care.meow_care_service.mapper.ChatRoomMapper;
import com.meow_care.meow_care_service.repositories.ChatRoomRepository;
import com.meow_care.meow_care_service.services.ChatRoomService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomServiceImpl extends BaseServiceImpl<ChatRoomDto, ChatRoom, ChatRoomRepository, ChatRoomMapper>
        implements ChatRoomService {
    public ChatRoomServiceImpl(ChatRoomRepository repository, ChatRoomMapper mapper) {
        super(repository, mapper);
    }
}
