package com.example.BE_number_blind_date.chat.Dto;

import com.example.BE_number_blind_date.chat.Entity.Chat;
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

    public DtoChatRoomList(Chat chat) {
        this.chatRoomId = chat.getChatRoomId();
        this.chatRoomName = chat.getCreator().getNickname();
        this.ownerEmail = chat.getCreator().getEmail();
        this.senderEmail = chat.getReceiver().getEmail();
        this.createdAt = chat.getCreatedDate();
    }
}
