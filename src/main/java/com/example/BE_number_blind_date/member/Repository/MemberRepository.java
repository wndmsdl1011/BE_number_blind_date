package com.example.BE_number_blind_date.member.Repository;

import com.example.BE_number_blind_date.member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    Member findByUserName(String userName);
}
