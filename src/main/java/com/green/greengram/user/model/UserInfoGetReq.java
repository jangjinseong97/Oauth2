package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@Setter
@ToString
public class UserInfoGetReq {
    @Schema(name = "signed_user_id", example = "1", type = "qwe",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private long signedUserId;
    @Schema(name = "profile_user_id",example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    private long profileUserId;

    @ConstructorProperties({"signed_user_id", "profile_user_id"})
    public UserInfoGetReq(long signedUserId, long profileUserId) {
        this.signedUserId = signedUserId;
        this.profileUserId = profileUserId;
    }
}
