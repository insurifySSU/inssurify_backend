package com.example.ensurify.repository;

import com.example.ensurify.domain.stomp.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
}