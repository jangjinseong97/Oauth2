package com.green.greengram.feed.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
@EqualsAndHashCode
public class FeedDelReq {
    private long feedId;
    private long signedUserId;

    @ConstructorProperties({"feed_id", "signed_user_id"})
    public FeedDelReq(long feedId, long signedUserId) {
        this.feedId = feedId;
        this.signedUserId = signedUserId;
    }
}
