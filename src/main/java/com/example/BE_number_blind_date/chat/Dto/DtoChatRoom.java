package com.example.BE_number_blind_date.chat.Dto;

import com.example.BE_number_blind_date.member.Entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DtoChatRoom {

    private Long chatRoomId;
    private String owner;  // 방장 닉네임
    private String sender; // 상대방 닉네임

    public DtoChatRoom(Long chatRoomId, Member creator, Member receiver) {
        this.chatRoomId = chatRoomId;
        this.owner = creator.getNickname();
        this.sender = receiver.getNickname();
    }
}
