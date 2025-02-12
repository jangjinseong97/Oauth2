package com.green.greengram.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
//@Table(name = "feed_table") 테이블명을 클래스명이 아닌 다른걸 넣고 싶을 때
public class Feed extends UpdatedAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @ManyToOne // 일 대 다 관계 앞(many)이 클래스 뒤(one)가 객체
    @JoinColumn(name = "writer_user_id", nullable = false)
    private User writerUser;
    // user 랑 feed의 관계이므로 writerUserId가 아닌 User 객체로 들어가야 된다.
    @Column(length = 1_000)
    private String contents;
    @Column(length = 30)
    private String location;
}
