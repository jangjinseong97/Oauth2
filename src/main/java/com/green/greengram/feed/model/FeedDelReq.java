package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@EqualsAndHashCode
public class FeedDelReq {
    private long feedId;
    @JsonIgnore
    private long signedUserId;

    @ConstructorProperties({"feedId"})
    public FeedDelReq(long feedId) {
        this.feedId = feedId;
    }

    public void setSignedUserId(long signedUserId) {
        this.signedUserId = signedUserId;
    }
}
