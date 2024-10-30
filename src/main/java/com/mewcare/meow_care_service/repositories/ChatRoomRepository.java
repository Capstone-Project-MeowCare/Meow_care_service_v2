package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
}