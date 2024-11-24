package com.example.ensurify.service;

import com.example.ensurify.common.apiPayload.code.status.ErrorStatus;
import com.example.ensurify.common.apiPayload.exception.GeneralException;
import com.example.ensurify.domain.User;
import com.example.ensurify.domain.stomp.MeetingRoom;
import com.example.ensurify.domain.stomp.UserMeetingRoom;
import com.example.ensurify.domain.stomp.actions.Check;
import com.example.ensurify.dto.request.CheckRequest;
import com.example.ensurify.repository.CheckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheckService {

    private final CheckRepository checkRepository;
    private final MeetingRoomService meetingRoomService;
    private final UserService userService;

    /**
     * 체크 내역 저장
     */
    @Transactional
    public void save(CheckRequest request, Long userId) {

        User user = userService.findById(userId);

        // 1. MeetingRoom 조회: 없으면 예외를 던짐
        MeetingRoom meetingRoom = meetingRoomService.findById(request.getMeetingRoomId());

        // 2. userId가 해당 MeetingRoom에 속한 유저인지 확인
        boolean isUserInRoom = meetingRoom.getUserMeetingRooms().stream()
                .anyMatch(userMeetingRoom -> userMeetingRoom.getUser().equals(user));
        if (!isUserInRoom) {
            throw new GeneralException(ErrorStatus.USER_NOT_IN_ROOM);  // 계약 당사자가 아닌 유저이면 예외 발생
        }

        // 3. 체크박스 숫자 유효성 검사
        if(meetingRoom.getContractDocument().getCheckTotal() < request.getCheckNum())
            throw new GeneralException(ErrorStatus.CHECK_NUM_NOT_FOUND);

        // 4. 체크 내역 저장(이미 존재할 경우 저장 X)
        if(!checkRepository.existsByCheckNumAndMeetingRoomId(request.getCheckNum(), meetingRoom.getId())) {

            Check check = Check.builder()
                    .checkNum(request.getCheckNum())
                    .meetingRoom(meetingRoom)
                    .build();

            checkRepository.save(check);
        }
    }
}
