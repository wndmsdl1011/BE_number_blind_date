package com.example.BE_number_blind_date.chatroom.Dto;

import com.example.BE_number_blind_date.member.Entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DtoCreateChatRoom {

    private Long chatRoomId;
    private String owner;  // 방장 닉네임
    private String sender; // 상대방 닉네임
    private LocalDate createdAt; // 생성일

    public DtoCreateChatRoom(Long chatRoomId, Member creator, Member receiver, LocalDate createdAt) {
        this.chatRoomId = chatRoomId;
        this.owner = creator.getNickname();
        this.sender = receiver.getNickname();
        this.createdAt = createdAt;
    }

}
