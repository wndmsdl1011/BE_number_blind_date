package com.example.BE_number_blind_date.message.Entity;

import com.example.BE_number_blind_date.chatroom.Entity.ChatRoom;
import com.example.BE_number_blind_date.member.Entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "chat_messages")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_massage_id")
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Member sender;  // 보낸사람

    private String content; // 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    @JsonIgnore
    private ChatRoom chatRoom;



}
