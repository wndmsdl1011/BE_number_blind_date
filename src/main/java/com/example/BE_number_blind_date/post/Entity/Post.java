package com.example.BE_number_blind_date.post.Entity;

import com.example.BE_number_blind_date.member.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "POST")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "MEMBER_ID")
    private Member member;



    @Column(name = "POST_MBTI", nullable = false, length = 4)
    private String mbti;

    @Column(name = "POST_HEIGHT", nullable = false, length = 3)
    private int height;

    @Column(name = "POST_HOBBY", length = 30)
    private String hobby;

    @Column(name = "POST_HIGHLIGHT")
    private String highlight;




}
