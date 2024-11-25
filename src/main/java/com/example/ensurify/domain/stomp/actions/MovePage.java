package com.example.ensurify.domain.stomp.actions;

import com.example.ensurify.domain.common.BaseEntity;
import com.example.ensurify.domain.stomp.MeetingRoom;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovePage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int pageNum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meeting_room_id")
    private MeetingRoom meetingRoom;
}