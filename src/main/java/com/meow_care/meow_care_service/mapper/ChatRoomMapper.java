package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.ChatRoomDto;
import com.meow_care.meow_care_service.entities.ChatRoom;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {MessageMapper.class})
public interface ChatRoomMapper extends BaseMapper<ChatRoomDto, ChatRoom> {

}