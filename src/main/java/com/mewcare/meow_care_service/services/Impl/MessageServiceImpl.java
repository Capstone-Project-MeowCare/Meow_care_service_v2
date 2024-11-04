package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.MessageDto;
import com.mewcare.meow_care_service.entities.Message;
import com.mewcare.meow_care_service.mapper.MessageMapper;
import com.mewcare.meow_care_service.repositories.MessageRepository;
import com.mewcare.meow_care_service.services.MessageService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageDto, Message, MessageRepository, MessageMapper>
        implements MessageService {
    public MessageServiceImpl(MessageRepository repository, MessageMapper mapper) {
        super(repository, mapper);
    }
}
