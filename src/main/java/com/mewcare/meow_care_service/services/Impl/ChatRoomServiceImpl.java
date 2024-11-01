package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.ChatRoomDto;
import com.mewcare.meow_care_service.entities.ChatRoom;
import com.mewcare.meow_care_service.mapper.ChatRoomMapper;
import com.mewcare.meow_care_service.repositories.ChatRoomRepository;
import com.mewcare.meow_care_service.services.ChatRoomService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class ChatRoomServiceImpl extends BaseServiceImpl<ChatRoomDto, ChatRoom, ChatRoomRepository, ChatRoomMapper>
        implements ChatRoomService {
    public ChatRoomServiceImpl(ChatRoomRepository repository, ChatRoomMapper mapper) {
        super(repository, mapper);
    }
}
