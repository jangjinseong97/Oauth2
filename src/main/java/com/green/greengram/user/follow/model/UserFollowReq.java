package com.green.greengram.user.follow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter

@EqualsAndHashCode
public class UserFollowReq {
    @JsonProperty("to_user_id")
    @Schema(name = "to_user_id", description = "팔로잉 유저 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private long toUserId;
    @JsonProperty("from_user_id")
    @Schema(name = "from_user_id", description = "팔로워 유저 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @Setter
    private long fromUserId;

    @ConstructorProperties({"to_user_id"})
    public UserFollowReq(long toUserId) {
        this.toUserId = toUserId;
    }
}
