package com.example.BE_number_blind_date.chatroom.Dto;

import com.example.BE_number_blind_date.chatroom.Entity.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DtoChatRoomList {

    private Long chatRoomId;
    private String chatRoomName;        // 채팅방 이름 (방장 닉네임)
    private String ownerEmail;       // 방장 이메일, 포스트익 생성자
    private String senderEmail;      // 채팅을 건 사람, 본인
    private LocalDate createdAt;

    public DtoChatRoomList(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getChatRoomId();
        this.chatRoomName = chatRoom.getCreator().getNickname();
        this.ownerEmail = chatRoom.getCreator().getEmail();
        this.senderEmail = chatRoom.getReceiver().getEmail();
        this.createdAt = chatRoom.getCreatedDate();
    }
}
