package com.example.BE_number_blind_date.message.Repository;

import com.example.BE_number_blind_date.message.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
