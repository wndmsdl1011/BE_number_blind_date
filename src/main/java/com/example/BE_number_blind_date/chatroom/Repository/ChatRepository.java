package com.example.BE_number_blind_date.chatroom.Repository;

import com.example.BE_number_blind_date.chatroom.Entity.ChatRoom;
import com.example.BE_number_blind_date.member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByCreatorAndReceiver(Member creator, Member receiver);

    List<ChatRoom> findByCreatorOrReceiver(Member creator, Member receiver);

}
