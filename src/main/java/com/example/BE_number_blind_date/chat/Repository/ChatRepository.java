package com.example.BE_number_blind_date.chat.Repository;

import com.example.BE_number_blind_date.chat.Entity.Chat;
import com.example.BE_number_blind_date.member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByCreatorAndReceiver(Member creator, Member receiver);

    List<Chat> findByCreatorOrReceiver(Member creator, Member receiver);

}
