package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Many;

@Getter
@Setter
@Entity
public class FeedComment extends UpdatedAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedCommentId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;
    @Column(nullable = false, length = 150)
    private String comment;
}
