package com.example.BE_number_blind_date.message.Dto;

import com.example.BE_number_blind_date.member.Entity.Member;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoChatMessage {

    private Long chatRoomId;
    private Member sender;  // 보낸사람
    private String content; // 내용


}
