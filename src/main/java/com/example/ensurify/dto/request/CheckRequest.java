package com.example.ensurify.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
public class CheckRequest {

    private Long meetingRoomId;
    private int checkNum;
    private boolean checked;
}
