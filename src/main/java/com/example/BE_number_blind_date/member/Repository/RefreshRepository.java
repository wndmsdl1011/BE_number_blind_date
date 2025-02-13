package com.example.BE_number_blind_date.member.Repository;

import com.example.BE_number_blind_date.member.Entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {


    Optional<RefreshEntity> findByUsername(String email);

    Boolean existsByRefresh(String refresh);

    void deleteByRefresh(String refresh);
}
