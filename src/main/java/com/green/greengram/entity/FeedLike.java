package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FeedLike extends CreatedAt{
    @EmbeddedId
    private FeedLikeIds feedLikeIds;
    @ManyToOne
    @JoinColumn(name = "feed_id")
    @MapsId("feedId")
    private Feed feed;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;
}
