package com.green.greengram.feed.like.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FeedLikeReq {
    private long feedId;
    private long userId;
}
